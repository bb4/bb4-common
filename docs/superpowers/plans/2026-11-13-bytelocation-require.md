# ByteLocation require validation Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Make `ByteLocation`'s Int constructor reject out-of-range values with `require` instead of silently truncating or relying on a disableable `assert`.

**Architecture:** Keep the public case-class API. Remove the redundant primary-constructor `assert`. Validate Int coordinates in the auxiliary constructor with `require` for range `[-128, 127]` before `toByte`. Update suite tests that currently document truncation.

**Tech Stack:** Scala, Scalatest (`AnyFunSuite`), Gradle (`./gradlew test`).

## Global Constraints

- Accepted Int range: `[-128, 127]` (full signed byte).
- Out-of-range → `IllegalArgumentException` via `require` (not `AssertionError`).
- Do not change `serialVersionUID` or other API surface.
- Spec: `docs/superpowers/specs/2026-11-13-bytelocation-require-design.md`.

---

## File map

| File | Role |
|------|------|
| `scala-source/com/barrybecker4/common/geometry/ByteLocation.scala` | Production: docs, remove assert, add require in Int ctor |
| `scala-test/com/barrybecker4/common/geometry/ByteLocationSuite.scala` | Tests: boundary success + out-of-range `IllegalArgumentException` |

---

### Task 1: Failing tests for Int-constructor validation

**Files:**
- Modify: `scala-test/com/barrybecker4/common/geometry/ByteLocationSuite.scala`
- Test: same file

**Interfaces:**
- Consumes: `new ByteLocation(irow: Int, icol: Int)`
- Produces: suite expects `IllegalArgumentException` for out-of-range; allows `-128`

- [ ] **Step 1: Replace truncation / AssertionError tests with require-based expectations**

In `ByteLocationSuite.scala`, replace the five Int-constructor edge-case tests (currently around lines 53–87) with:

```scala
  test("Int constructor at full signed byte range") {
    val loc1 = new ByteLocation(-128, 127)
    assertResult(-128) { loc1.row }
    assertResult(127) { loc1.col }

    val loc2 = new ByteLocation(127, -128)
    assertResult(127) { loc2.row }
    assertResult(-128) { loc2.col }
  }

  test("Int constructor rejects row 128") {
    assertThrows[IllegalArgumentException] {
      new ByteLocation(128, 0)
    }
  }

  test("Int constructor rejects row -129") {
    assertThrows[IllegalArgumentException] {
      new ByteLocation(-129, 0)
    }
  }

  test("Int constructor rejects large positive row 200") {
    assertThrows[IllegalArgumentException] {
      new ByteLocation(200, 0)
    }
  }

  test("Int constructor rejects row 256") {
    assertThrows[IllegalArgumentException] {
      new ByteLocation(256, 10)
    }
  }

  test("Int constructor rejects col 128") {
    assertThrows[IllegalArgumentException] {
      new ByteLocation(0, 128)
    }
  }
```

Also update the existing `"Int constructor at boundary values"` test to keep `127` / `-127` as-is (still valid). Leave all non-constructor tests unchanged.

- [ ] **Step 2: Run suite — expect failures on out-of-range cases that still truncate**

Run: `./gradlew test --tests com.barrybecker4.common.geometry.ByteLocationSuite`

Expected: FAIL — e.g. `200` / `256` still construct without throwing; possibly `-128` still fails with `AssertionError` instead of succeeding.

- [ ] **Step 3: Commit tests**

```bash
git add scala-test/com/barrybecker4/common/geometry/ByteLocationSuite.scala
git commit -m "$(cat <<'EOF'
#53 Update ByteLocationSuite for require-based Int validation.

EOF
)"
```

---

### Task 2: Implement require validation in ByteLocation

**Files:**
- Modify: `scala-source/com/barrybecker4/common/geometry/ByteLocation.scala`
- Test: `scala-test/com/barrybecker4/common/geometry/ByteLocationSuite.scala`

**Interfaces:**
- Consumes: none new
- Produces: `def this(irow: Int, icol: Int)` with `require` on `[-128, 127]`; no primary `assert`

- [ ] **Step 1: Update ByteLocation source**

Replace the file body so it matches:

```scala
/* Copyright by Barry G. Becker, 2000-2018. Licensed under MIT License: http://www.opensource.org/licenses/MIT */
package com.barrybecker4.common.geometry

/**
  * Represents an immutable location of something in byte coordinates.
  * The range of each coordinate is [-128, 127].
  * @param brow the row coordinate [-128, 127].
  * @param bcol the column coordinate [-128, 127].
  */
// Note: changing serialVersionUID breaks deserialization of stored ByteLocation objects.
@SerialVersionUID(1L)
case class ByteLocation(brow: Byte, bcol: Byte) extends Location {

  def this(irow: Int, icol: Int) = {
    require(irow >= -128 && irow <= 127, s"row $irow out of byte range [-128, 127]")
    require(icol >= -128 && icol <= 127, s"col $icol out of byte range [-128, 127]")
    this(irow.toByte, icol.toByte)
  }
  override def row: Int = brow
  override def col: Int = bcol
  override def getX: Int = bcol
  override def getY: Int = brow

  override def incrementOnCopy(rowChange: Int, colChange: Int) =
    new ByteLocation(row + rowChange, col + colChange)
}
```

Notes:
- Use `<= 127` (equivalent to `< 128` from the issue suggestion).
- Remove the primary-constructor `assert` entirely.

- [ ] **Step 2: Run suite — expect pass**

Run: `./gradlew test --tests com.barrybecker4.common.geometry.ByteLocationSuite`

Expected: BUILD SUCCESSFUL, all tests pass.

- [ ] **Step 3: Run full test suite**

Run: `./gradlew test`

Expected: BUILD SUCCESSFUL.

- [ ] **Step 4: Commit implementation**

```bash
git add scala-source/com/barrybecker4/common/geometry/ByteLocation.scala
git commit -m "$(cat <<'EOF'
#53 Require Int coords in ByteLocation range before toByte.

EOF
)"
```

---

## Spec coverage checklist

| Spec item | Task |
|-----------|------|
| Remove primary `assert` | Task 2 |
| `require` in Int ctor for `[-128, 127]` | Task 2 |
| `IllegalArgumentException` messages | Task 2 |
| Fix docs to `[-128, 127]` | Task 2 |
| Keep `serialVersionUID` | Task 2 (unchanged) |
| Boundary `-128` / `127` tests | Task 1 |
| Out-of-range reject tests; drop truncation | Task 1 |
