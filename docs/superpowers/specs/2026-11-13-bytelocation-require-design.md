# ByteLocation constructor validation (issue #53)

## Problem

`ByteLocation`'s Int auxiliary constructor converts with `toByte` without checking range, which silently truncates (e.g. `new ByteLocation(300, 400)` → `(44, -112)`). The primary constructor uses `assert`, which is redundant for `Byte` values and can be disabled with `-da`.

## Goal

Always reject out-of-range Int coordinates with a clear error; allow the full signed byte range `[-128, 127]`.

## Design

### Construction and validation

- Remove the primary-constructor `assert` (`Byte` already bounds values).
- In the Int auxiliary constructor, `require` both coordinates in `[-128, 127]` before calling `toByte`.
- Out-of-range values throw `IllegalArgumentException` with messages like `row $irow out of byte range [-128, 127]` (and likewise for col).
- Update class/param docs to state signed byte range `[-128, 127]` (remove incorrect "0–255" wording).
- Do not change `serialVersionUID` or other API surface.

### Tests

Update `ByteLocationSuite`:

- Boundary: `new ByteLocation(-128, 127)` (and col equivalents) succeed.
- Out of range (`128`, `-129`, `200`, `256`, etc.) throw `IllegalArgumentException` — not `AssertionError`, and not silent truncation.
- Remove/replace tests that expect truncation (`200 → -56`, `256 → 0`).
- Leave unrelated equals/distance/increment tests unchanged.

## Out of scope

- Making the case class primary constructor private or introducing a factory-only API.
- Changing `incrementOnCopy` beyond inheriting the new Int-constructor validation (overflow will fail loudly instead of truncating).
