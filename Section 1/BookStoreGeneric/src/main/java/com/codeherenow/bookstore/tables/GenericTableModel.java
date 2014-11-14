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

package com.codeherenow.bookstore.tables;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * This is an improved version (albeit teeny tiny :P) of the {@link com.codeherenow.bookstore.tables.BooksTableModel}.
 * Since this is a generic class, you can use it with any POJO. Just extend it and implement the
 * 'getValue(String, T)' method.
 *
 * @author Ragunath Jawahar <ragunathjawahar@gmail.com>
 */
public abstract class GenericTableModel<T> extends DefaultTableModel {
    protected List<T> mPojos;

    public GenericTableModel(String[] columns, List<T> pojos) {
        if (columns == null || columns.length == 0) {
            throw new IllegalArgumentException("Specify at least one column.");
        }
        // Add columns
        setColumnIdentifiers(columns);

        // Initialization
        mPojos = new ArrayList<T>();

        // Add rows
        for (T rowData : pojos) {
            addRow(rowData);
        }
    }

    public final void addRow(T rowData) {
        mPojos.add(rowData);

        Vector row = new Vector();
        for (Object column : columnIdentifiers) {
            row.add(getValue((String) column, rowData));
        }
        addRow(row);
    }

    protected abstract String getValue(String columnName, T rowData);
}
