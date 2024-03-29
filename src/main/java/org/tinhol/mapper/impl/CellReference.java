package org.tinhol.mapper.impl;/*
 * Copyright 2013 Haulmont
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

import org.apache.commons.lang3.ObjectUtils;
import org.jetbrains.annotations.NotNull;
import org.xlsx4j.sml.Cell;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CellReference implements Comparable<CellReference> {
    public static final Pattern CELL_COORDINATES_PATTERN = Pattern.compile("([A-z]+)([0-9]+)");
    private int column;
    private int row;
    private final String sheet;

    public CellReference(String sheet, int row, int column) {
        this.column = column;
        this.row = row;
        this.sheet = sheet;
    }

    public CellReference(String sheet, String cellRef) {
        Matcher matcher = CELL_COORDINATES_PATTERN.matcher(cellRef);
        if (matcher.find()) {
            column = XlsxUtils.getNumberFromColumnReference(matcher.group(1));
            row = Integer.parseInt(matcher.group(2));
            this.sheet = sheet;
        } else {
            throw new RuntimeException(String.format("Wrong cell %s", cellRef));
        }
    }

    public CellReference(String sheet, Cell cell) {
        this(sheet, cell.getR());
    }

    public CellReference shift(int downShift, int rightShift) {
        row += downShift;
        column += rightShift;
        return this;
    }

    public CellReference move(int row, int col) {
        this.row = row;
        this.column = col;
        return this;
    }

    public String toReference() {
        return XlsxUtils.getColumnReferenceFromNumber(getColumn()) + getRow();
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public String getSheet() {
        return sheet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CellReference that = (CellReference) o;

        if (column != that.column) return false;
        if (row != that.row) return false;
        return Objects.equals(sheet, that.sheet);
    }

    @Override
    public int hashCode() {
        int result = column;
        result = 31 * result + row;
        result = 31 * result + (sheet != null ? sheet.hashCode() : 0);
        return result;
    }

    @Override
    public int compareTo(@NotNull CellReference o) {
        int rows = ObjectUtils.compare(row, ((CellReference) o).row);
        int columns = ObjectUtils.compare(column, ((CellReference) o).column);
        return rows != 0 ? rows : columns;
    }
}