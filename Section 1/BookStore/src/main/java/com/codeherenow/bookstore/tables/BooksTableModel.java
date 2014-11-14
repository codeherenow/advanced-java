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

import com.codeherenow.bookstore.models.Book;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.Vector;

/**
 * An extension of the {@link javax.swing.table.DefaultTableModel}, that takes care of converting
 * {@link com.codeherenow.bookstore.models.Book} into row data for the {@link javax.swing.JTable}.
 *
 * @author Ragunath Jawahar <ragunathjawahar@gmail.com>
 */
public class BooksTableModel extends DefaultTableModel {

    public BooksTableModel(final List<Book> books) {
        setColumnIdentifiers(new String[]{ "Title", "Author", "ISBN", "Price" });

        // Add rows
        for (Book book : books) {
            addRow(book);
        }
    }

    public void addRow(Book book) {
        Vector<String> row = new Vector<String>();
        row.add(book.getTitle());
        row.add(book.getAuthor());
        row.add(book.getIsbn());
        row.add(String.valueOf(book.getPrice()));
        addRow(row);
    }
}
