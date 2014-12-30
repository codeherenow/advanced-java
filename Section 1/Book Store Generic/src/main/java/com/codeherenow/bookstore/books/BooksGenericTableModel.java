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

package com.codeherenow.bookstore.books;

import com.codeherenow.bookstore.common.GenericTableModel;

import java.util.List;

/**
 * This adapter has no code for managing rows and columns on its own. It extends the
 * {@link com.codeherenow.bookstore.common.GenericTableModel} and override a convenience method that returns a value
 * for the given column.
 *
 * @author Ragunath Jawahar <www.codeherenow.com>
 */
public class BooksGenericTableModel extends GenericTableModel<Book> {

    public BooksGenericTableModel(String[] columns, List<Book> pojos) {
        super(columns, pojos);
    }

    @Override
    protected String getValue(String columnName, Book book) {
        String value;

        if ("Title".equals(columnName)) {
            value = book.getTitle();
        } else if ("Author".equals(columnName)) {
            value = book.getAuthor();
        } else if ("ISBN".equals(columnName)) {
            value = book.getIsbn();
        } else if ("Author".equals(columnName)) {
            value = book.getAuthor();
        } else if ("Price".equals(columnName)) {
            value = String.valueOf(book.getPrice());
        } else {
            String message = String.format("Cannot find column: %s", columnName);
            throw new IllegalArgumentException(message);
        }

        /*
         * Java 7 allows 'java.lang.String' types in switch statements. The above 'if else' block can be rewritten as
         * follows. Do check out my 'Learn Java 8 and Java 7' course - https://www.udemy.com/learn-java-8-and-java-7/
         *
         *  switch (columnName) {
         *      case "Title"    :   value = book.getTitle();    break;
         *      case "Author"   :   value = book.getAuthor();   break;
         *      case "ISBN"     :   value = book.getIsbn();     break;
         *      case "Price"    :   value = book.getPrice();    break;
         *
         *      default:
         *          String message = String.format("Cannot find column: %s", columnName);
         *          throw new IllegalStateException(message);
         *  }
         */

        return value;
    }
}
