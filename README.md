## Sudoku API

##### In Progress


## Solve 
POST localhost:8080/solve

### Request example
- Easy
```json
{
	"matrix": [
        [0,0,0,4,0,0,0,0,0],
        [0,0,5,1,2,6,7,3,4],
        [4,6,0,0,0,3,0,0,0],
        [0,0,4,0,0,1,0,6,8],
        [0,1,0,6,7,8,0,0,2],
        [0,0,6,2,0,5,0,0,7],
        [6,5,2,8,0,0,4,0,0],
        [9,0,0,0,6,0,0,5,0],
        [1,7,8,3,5,0,9,0,0]
        ]
}
```

- Hard
```json
{
	"matrix": [
        [2,0,4,0,0,0,1,9,0],
        [9,0,3,4,0,0,0,6,0],
        [6,0,7,0,0,0,3,4,2],
        [0,3,0,0,6,0,0,0,0],
        [1,6,0,0,0,8,0,0,0],
        [0,7,0,0,3,0,6,8,0],
        [3,4,0,8,0,1,9,0,0],
        [7,0,0,0,4,0,8,0,0],
        [0,0,0,2,0,6,4,0,0]
        ]
}
```