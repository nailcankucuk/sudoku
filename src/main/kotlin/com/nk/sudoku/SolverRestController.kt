package com.nk.sudoku

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SolverRestController(val solverService: SolverService) {
    @PostMapping("/solve")
    fun solve(@RequestBody sudoku: Sudoku): Sudoku {
        return solverService.solve(sudoku)
    }
}