/*
 * Copyright 2014, Ragunath Jawahar (www.codeherenow.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codeherenow.bookstore.common;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.*;
import java.util.*;

/**
 * This is the first attempt to create a {@link javax.swing.table.TableModel} using reflection.
 * We'll keep improving in the upcoming lectures.
 *
 * @author Ragunath Jawahar <www.codeherenow.com>
 */
public class SmartTableModel<T> extends DefaultTableModel {

    public SmartTableModel(Class<T> modelClass, List<T> pojos) {
        /*
         * Class.getFields() will return the fields that can be accessed within the current
         * access scope (depending upon where your access them reflectively). Since all fields
         * are private, we need to get `all` the fields declared by the Class.
         */
        AccessibleObject[] columnAccessibleObjects = getColumnAccessibleObjects(modelClass);

        // Column names
        setColumnIdentifiers(getColumnNames(columnAccessibleObjects));

        // Add rows
        addRows(columnAccessibleObjects, pojos);
    }

    private AccessibleObject[] getColumnAccessibleObjects(Class<T> modelClass) {
        List<AccessibleObject> columnAccessibleObjects = new ArrayList<AccessibleObject>();

        // Fields
        Field[] declaredFields = modelClass.getDeclaredFields();
        for (Field field : declaredFields) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                columnAccessibleObjects.add(field);
            }
        }

        // Methods
        Method[] methods = modelClass.getDeclaredMethods();
        for (Method method : methods) {
            Column column = method.getAnnotation(Column.class);
            if (column != null && isCompatibleMethod(method)) {
                columnAccessibleObjects.add(method);
            }
        }

        // Sort
        Collections.sort(columnAccessibleObjects, new ColumnAccessibleObjectComparator());

        // Convert to array
        return columnAccessibleObjects.toArray(new AccessibleObject[columnAccessibleObjects.size()]);
    }

    private boolean isCompatibleMethod(Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getParameterCount() == 0;
    }

    private Vector<String> getColumnNames(AccessibleObject[] accessibleObjects) {
        Vector<String> columnNames = new Vector<String>();
        for (AccessibleObject accessibleObject : accessibleObjects) {
            Column columnAnnotation = accessibleObject.getAnnotation(Column.class);
            boolean annotationHasColumnName = columnAnnotation != null
                    && !"$null".equals(columnAnnotation.name());

            String columnName;
            if (annotationHasColumnName) {
                columnName = columnAnnotation.name();
            } else if (accessibleObject instanceof Field) {
                columnName = toColumnName(((Field) accessibleObject).getName());
            } else if (accessibleObject instanceof Method) {
                columnName = toColumnName(((Method) accessibleObject).getName());
            } else {
                throw new RuntimeException("This cannot happen!");
            }

            columnNames.add(columnName);
        }
        return columnNames;
    }

    /**
     * Converts camel case names to humanized strings.
     * (e.g.)
     *     1. "name" becomes "Name"
     *     2. "superPowers" becomes "Super Powers"
     *
     * @param fieldName  A camel case {@link String}.
     * @return Humanized string.
     */
    private String toColumnName(String fieldName) {
        String humanizedString = StringUtils.join(
                StringUtils.splitByCharacterTypeCamelCase(fieldName),
                ' ');
        return WordUtils.capitalizeFully(humanizedString);
    }

    private void addRows(AccessibleObject[] accessibleObjects, List<T> pojos) {
        for (T pojo : pojos) {
            addRow(accessibleObjects, pojo);
        }
    }

    private void addRow(AccessibleObject[] accessibleObjects, T pojo) {
        Vector rowData = new Vector();
        for (AccessibleObject accessibleObject : accessibleObjects) {
            Object value = accessibleObject instanceof Field
                    ? getValueFromField((Field) accessibleObject, pojo)
                    : getValueFromMethod((Method) accessibleObject, pojo);
            rowData.add(value);
        }
        addRow(rowData);
    }

    private Object getValueFromField(Field field, T pojo) {
        Object value = null;
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            value = field.get(pojo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return value;
    }

    private Object getValueFromMethod(Method method, T pojo) {
        Object value = null;
        try {
            value = method.invoke(pojo);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return value;
    }

    /**
     * A {@link java.util.Comparator} to sort fields based on the {@link com.codeherenow.bookstore.common.Column#index()}
     * attribute.
     */
    static class ColumnAccessibleObjectComparator implements Comparator<AccessibleObject> {

        @Override
        public int compare(AccessibleObject lhs, AccessibleObject rhs) {
            Column lhsColumn = lhs.getAnnotation(Column.class);
            Column rhsColumn = rhs.getAnnotation(Column.class);

            int lhsIndex = lhsColumn.index();
            int rhsIndex = rhsColumn.index();

            return lhsIndex > rhsIndex ? 1
                    : lhsIndex < rhsIndex ? -1 : 0;
        }
    }
}
