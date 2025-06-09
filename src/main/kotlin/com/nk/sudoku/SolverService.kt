package com.nk.sudoku

import org.springframework.stereotype.Service
import kotlin.collections.flatten


@Service
class SolverService {
    /*
        [2,0,4,0,0,0,1,9,0],
        [9,0,3,4,0,0,0,6,0],
        [6,0,7,0,0,0,3,4,2],
        [0,3,0,0,6,0,0,0,0],
        [1,6,0,0,0,8,0,0,0],
        [0,7,0,0,3,0,6,8,0],
        [3,4,0,8,0,1,9,0,0],
        [7,0,0,0,4,0,8,0,0],
        [0,0,0,2,0,6,4,0,0]
     */
    fun solve(sudoku: Sudoku): Sudoku {
        val analyser = sudoku.toAnalyser()
        var iterationCount = 0
        var inProgress = true
        while (iterationCount < 10 || inProgress) {
            analyser.findPossibleNumbersForRows()
            analyser.findPossibleNumbersForColumn()
            analyser.findPossibleNumbersForSquare()
            analyser.find()
            iterationCount++
            inProgress = analyser.emptyBoxCount() != 0
        }
        return analyser.toSudoku()
    }
}

fun Sudoku.toAnalyser(): SudokuAnalyser {
    val matrix = this.matrix.map { row -> row.map { Box(it) }.toTypedArray() }.toTypedArray()
    return SudokuAnalyser(matrix)
}

fun SudokuAnalyser.findPossibleNumbersForRows() {
    this.matrix.forEach { row ->
        val existingValues = row.map { it.value }.filter { it != 0 }.toSet()
        val pnRow = arrayOf(1, 2, 3, 4, 5, 6, 7, 8, 9).filter { !existingValues.contains(it) }.toSet()
        row.filter { it.value == 0 }.forEach { box -> box.pnRow = pnRow }
    }
}

fun SudokuAnalyser.findPossibleNumbersForColumn() {
    val reversedMatrix = Array(9) { x -> Array(9) { y -> this.matrix[y][x] } }

    reversedMatrix.forEach { row ->
        val existingValues = row.map { it.value }.filter { it != 0 }.toSet()
        val pnColumn = (1..9).filter { it !in existingValues }.toSet()
        row.filter { it.value == 0 }.forEach { box -> box.pnColumn = pnColumn }
    }
}

fun SudokuAnalyser.findPossibleNumbersForSquare() {
    for (xs in 0..8 step 3) {
        for (ys in 0..8 step 3) {
            val squareBoxes = mutableListOf<Box>()
            for (x in 0..2) {
                for (y in 0..2) {
                    squareBoxes.add(matrix[xs + x][ys + y])
                }
            }
            val existingValues = squareBoxes.map { it.value }.filter { it != 0 }.toSet()
            val pnSquare = (1..9).filter { it !in existingValues }.toSet()
            for (x in 0..2) {
                for (y in 0..2) {
                    if (matrix[xs + x][ys + y].value == 0) {
                        matrix[xs + x][ys + y].pnSquare = pnSquare
                    }
                }
            }
        }
    }
}

fun SudokuAnalyser.find() {
    this.matrix.forEach { row ->
        row.filter { it.value == 0 }.forEach { box ->
            val commonNumbers = box.pnRow.intersect(box.pnColumn).intersect(box.pnSquare)
            println("commonNumbers for $box is $commonNumbers")
            if (commonNumbers.size == 1) box.value = commonNumbers.first()
        }
    }
}

fun SudokuAnalyser.emptyBoxCount(): Int {
    return this.matrix.flatten().count { it.value == 0 }
}

fun SudokuAnalyser.toSudoku(): Sudoku {
    val sudokuMatrix: Array<IntArray> = this.matrix.map { row -> row.map { it.value }.toIntArray() }.toTypedArray()
    return Sudoku(sudokuMatrix)
}

data class SudokuAnalyser(
    val matrix: Array<Array<Box>>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SudokuAnalyser

        if (!matrix.contentDeepEquals(other.matrix)) return false

        return true
    }

    override fun hashCode(): Int {
        return matrix.contentDeepHashCode()
    }
}

/* pn: possible numbers */
data class Box(
    var value: Int,
    var pnRow: Set<Int> = setOf(),
    var pnColumn: Set<Int> = setOf(),
    var pnSquare: Set<Int> = setOf()
) {
    override fun toString(): String {
        return "Box(value=$value, pnRow=$pnRow, pnColumn=$pnColumn, pbSquare=$pnSquare)"
    }
}