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

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

/**
 * This is an improved (albeit teeny tiny :P) version of the older {@code BooksTableModel}.
 * Since this is a generic class, you can use it with any POJO. Just extend it and implement the
 * 'getValue(String, T)' method.
 *
 * @author Ragunath Jawahar <www.codeherenow.com>
 */
public abstract class GenericTableModel<T> extends DefaultTableModel {

    public GenericTableModel(String[] columns, List<T> pojos) {
        if (columns == null || columns.length == 0) {
            throw new IllegalArgumentException("Specify at least one column.");
        }

        // Add columns
        setColumnIdentifiers(columns);

        // Add rows
        for (T pojo : pojos) {
            addRow(pojo);
        }
    }

    public final void addRow(T rowData) {
        Vector row = new Vector();
        for (Object column : columnIdentifiers) {
            row.add(getValue((String) column, rowData));
        }
        addRow(row);
    }

    protected abstract Object getValue(String columnName, T rowData);
}
