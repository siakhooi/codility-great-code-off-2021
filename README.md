# Codility Challenge: Great Code Off 2021

## CakeFactory

- Difficulty: Hard
- Count the number of cakes consisting of K different layers in the order 1, 2, ..., K that will be created after a sequence of operations of putting a layer on some interval.
- <https://app.codility.com/programmers/challenges/great_code_off2021/>

## Versions

- Result
  - `Good`: Correctness 100%, Performance 100%.
  - `OK`: Correctness 100%, Performance <100%.
  - `Fail`: Correctness <100%, Performance <100%.
- File naming convention
  - Code `A1`: `TheGreatCodeOff2021A1.java`
  - Code `B2`: `TheGreatCodeOff2021B2.java`
  - etc

### Gold Award

| Code | Complexity                                                        | Method  | Result |
| ---- | ----------------------------------------------------------------- | ------- | ------ |
| `T1` | `O(M*log(N))` or `O(N+M)` or `O((M+N)*log(N))` or `O(N+M*log(M))` | M/BTree | Good   |

### Silver Awards

| Code | Complexity                 | Method                 | Based on  | Description                                    | Result |
| ---- | -------------------------- | ---------------------- | --------- | ---------------------------------------------- | ------ |
| `A1` | `O(M*N)` or `O(M*sqrt(N))` | N/Individual           |           | Simple N\*K Search, `int[]` array              | OK     |
| `A2` | `O(M*N)` or `O(M*sqrt(N))` | N/Individual           |           | Simple N\*K Search, `int[]`,`boolean[]` arrays | OK     |
| `B1` | `O(M*N)` or `O(M*sqrt(N))` | N/Range                |           | `int[]` arrays                                 | OK     |
| `B2` | `O(M*N)` or `O(M*sqrt(N))` | N/Range                | `B1`      | simplified steps                               | OK     |
| `B3` | `O(M*N)` or `O(M*sqrt(N))` | N/Range                | `B2`      | remove unwanted segments                       | OK     |
| `B4` | `O(M*N)` or `O(M*sqrt(N))` | N/Range                | `B3`      | reverse orders                                 | OK     |
| `B5` | `O(M*N)` or `O(M*sqrt(N))` | N/Range                | `B4`      | remove result loop                             | OK     |
| `B6` | `O(M*N)` or `O(M*sqrt(N))` | N/Range                |           | `Segment` class, `boolean` status              | OK     |
| `C1` | `O(M*N)` or `O(M*sqrt(N))` | N/Group/Range          |           | `Group[]`, `Segment[]` arrays                  | OK     |
| `C2` | `O(M*N)` or `O(M*sqrt(N))` | N/Group/Range          | `C1`      | combine segments                               | OK     |
| `C3` | `O(M*N)` or `O(M*sqrt(N))` | N/Group/Range          | `C2`,`F2` | query M compressed                             | OK     |
| `C4` | `O(M*N)` or `O(M*sqrt(N))` | N/Group/Range          | `C3`      | break on full range                            | OK     |
| `D1` | `O(M*N)` or `O(M*sqrt(N))` | N/Group/Subgroup/Range |           |                                                | OK     |
| `E1` | `O(M*N)` or `O(M*sqrt(N))` | N/Tree                 |           |                                                | OK     |
| `E2` | `O(M*N)` or `O(M*sqrt(N))` | N/Tree                 | `E1`      | combine `Node`                                 | OK     |
| `E3` | `O(M*N)` or `O(M*sqrt(N))` | N/Tree                 | `E2`      | compress unwanted `Node`                       | OK     |
| `F1` | `O(M*N)` or `O(M*sqrt(N))` | N/Group/Tree           |           |                                                | OK     |
| `F2` | `O(M*N)` or `O(M*sqrt(N))` | N/Group/Tree           | `F1`      | query M compressed                             | OK     |
| `F3` | `O(M*N)` or `O(M*sqrt(N))` | N/Group/Tree           | `F2`      | RightNode array                                | OK     |
| `G1` | `O(M*sqrt(N))`             | N/Group/TruthTable     |           | `long[]` array                                 | OK     |
| `G2` | `O(M*sqrt(N))`             | N/Group/TruthTable     |           | using constants array                          | OK     |
| `G3` | `O(M*N)` or `O(M*sqrt(N))` | N/Group/TruthTable     |           | `LongNode` class                               | OK     |
| `I1` | `O(M*N)` or `O(M*sqrt(N))` | N/Group/BTree          |           | combine & compress                             | OK     |
| `I2` | `O(M*N)` or `O(M*sqrt(N))` | N/Group/BTree          | `I1`      |                                                | OK     |
| `J1` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree                |           | combine & compress                             | OK     |
| `J2` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree                |           | no combine & no compress                       | OK     |
| `J3` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree                | `J2`      | separate result loop                           | OK     |
| `J4` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree                | `J1`      | separate result loop                           | OK     |
| `J5` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree                | `J4`      | preexplode Btree                               | OK     |
| `J6` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree                | `J5`      | no combine                                     | OK     |
| `J7` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree                | `J4`      | preexploded, reduce process steps              | OK     |
| `J8` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree                | `J7`      | remove from,to, dividepoint                    | OK     |
| `J9` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree                | `J8`      | no combine                                     | OK     |
| `K1` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree(Array)         | `J5`      | compress                                       | OK     |
| `K2` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree(Array)         | `K1`      | use bit operator, eg change `*` to `<<`        | OK     |
| `K3` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree(Array)         | `K2`      | native arrays `int[]`, `boolean[]`             | OK     |
| `K4` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree(Array)         | `K3`      | remove redundant arrays & preexplode           | OK     |
| `K5` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree(Array)         | `K3`      | use `Node` class                               | OK     |
| `K6` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree(Array)         | `K5`      | reduce redundant updates                       | OK     |
| `K7` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree(Array)         | `K6`      | reduce process steps                           | OK     |
| `K8` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree(Array)         | `K7`      | add combine                                    | OK     |
| `K9` | `O(M*N)` or `O(M*sqrt(N))` | N/BTree(Array)         | `K4`      | native arrays `int[]`, `boolean[]`             | OK     |
| `L1` | `O(M*N)` or `O(M*sqrt(N))` | N/Tree/Individual      |           |                                                | OK     |
| `L2` | `O(M*N)` or `O(M*sqrt(N))` | N/Tree/Individual      | `L1`      | no combine, no valid check                     | OK     |

### Failed/Incomplete

| Code | Method            | Description                     | Result                       |
| ---- | ----------------- | ------------------------------- | ---------------------------- |
| `G0` | N/TruthTable      |                                 | Fail - Out Of Memory         |
| `G4` | N/TruthTable      | Cycling array on K              | Fail - Out Of Memory         |
| `H1` | N/Group           | prev/next link                  | Fail - Wrong Answer, not fix |
| `F4` | N/Group/Tree      | RightNode array, Search Parents | Fail - Wrong Answer, not fix |
| `M1` | N/Tree/TruthTable |                                 | Fail - Out Of Memory         |
| `N1` | K/BTree           |                                 | Fail - Wrong Answer, not fix |

### Truth Table for G series and M series

| Flavor(n-1) | Flavor(n) | Instruction | Status | New Flavor(n) | New Status |
| ----------- | --------- | ----------- | ------ | ------------- | ---------- |
| FALSE       | FALSE     | FALSE       | TRUE   | FALSE         | TRUE       |
| FALSE       | TRUE      | FALSE       | TRUE   | TRUE          | TRUE       |
| TRUE        | FALSE     | FALSE       | TRUE   | FALSE         | TRUE       |
| TRUE        | TRUE      | FALSE       | TRUE   | TRUE          | TRUE       |
| FALSE       | FALSE     | TRUE        | TRUE   | FALSE         | FALSE      |
| FALSE       | TRUE      | TRUE        | TRUE   | FALSE         | FALSE      |
| TRUE        | FALSE     | TRUE        | TRUE   | TRUE          | TRUE       |
| TRUE        | TRUE      | TRUE        | TRUE   | FALSE         | FALSE      |
| FALSE       | FALSE     | FALSE       | FALSE  | FALSE         | FALSE      |
| FALSE       | TRUE      | FALSE       | FALSE  | FALSE         | FALSE      |
| TRUE        | FALSE     | FALSE       | FALSE  | FALSE         | FALSE      |
| TRUE        | TRUE      | FALSE       | FALSE  | FALSE         | FALSE      |
| FALSE       | FALSE     | TRUE        | FALSE  | FALSE         | FALSE      |
| FALSE       | TRUE      | TRUE        | FALSE  | FALSE         | FALSE      |
| TRUE        | FALSE     | TRUE        | FALSE  | FALSE         | FALSE      |
| TRUE        | TRUE      | TRUE        | FALSE  | FALSE         | FALSE      |
