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

package com.codeherenow.bookstore;

import com.codeherenow.bookstore.books.BooksWindow;

import javax.swing.*;

/**
 * This is the entry point of the application. This class creates a new {@link com.codeherenow.bookstore.books.BooksWindow}
 * and makes it visible.
 *
 * @author Ragunath Jawahar <www.codeherenow.com>
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new BooksWindow("Toy Book Store").setVisible(true);
            }
        });

        /*
         * The above statement can be rewritten using a lambda expression. To learn about lambda expressions
         * check out my 'Learn Java 8 and Java 7' course - https://www.udemy.com/learn-java-8-and-java-7/
         *
         * SwingUtilities.invokeLater(() -> { new BookStoreWindow("Toy Book Store").setVisible(true); });
         */
    }
}
