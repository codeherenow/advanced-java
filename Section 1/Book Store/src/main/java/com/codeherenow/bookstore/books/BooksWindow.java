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

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * This class displays is responsible for rendering the UI for the application. It houses a {@link javax.swing.JTable}
 * inside a {@link javax.swing.JScrollPane} that displays few rows of {@link com.codeherenow.bookstore.books.Book}s.
 *
 * @author Ragunath Jawahar <www.codeherenow.com>
 */
public class BooksWindow extends JFrame {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 400;

    public BooksWindow(String title) {
        // Exit the app if 'X' button is pressed
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Set properties
        setTitle(title);
        setSize(WIDTH, HEIGHT);

        // Center window
        centerWindowOnScreen(WIDTH, HEIGHT);

        // Initialize the UI
        initUi();
    }

    private void centerWindowOnScreen(int width, int height) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int startX = (screenSize.width - width) / 2;
        int startY = (screenSize.height - height) / 2;
        setLocation(startX, startY);
    }

    private void initUi() {
        // Create a JTable, add it to a JScrollPane
        JTable booksTable = new JTable();
        add(new JScrollPane(booksTable));

        // Create a Table Model and display data
        List<Book> books = BooksRepository.getBooks();
        BooksTableModel booksTableModel = new BooksTableModel(books);
        booksTable.setModel(booksTableModel);
    }

}
