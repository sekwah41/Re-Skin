# v1.4.1 (Sun Jun 06 2021)

#### 🐛 Bug Fix

- chore(ci): Update CHANGELOG.md [#29](https://github.com/sekwah41/Re-Skin/pull/29) ([@sekwah41](https://github.com/sekwah41))
- chore(ci): Ignoring more build cases [#28](https://github.com/sekwah41/Re-Skin/pull/28) ([@sekwah41](https://github.com/sekwah41))
- chore(ci): Updating to stop fails [#27](https://github.com/sekwah41/Re-Skin/pull/27) ([@sekwah41](https://github.com/sekwah41))
- Updating changelog generation [#26](https://github.com/sekwah41/Re-Skin/pull/26) ([@sekwah41](https://github.com/sekwah41))
- Release skin fixes (fixes #24) [#25](https://github.com/sekwah41/Re-Skin/pull/25) ([@sekwah41](https://github.com/sekwah41))
- Update CI actions [#23](https://github.com/sekwah41/Re-Skin/pull/23) ([@sekwah41](https://github.com/sekwah41))
- Added /setmodel command & rewrote parts of /setskin [#22](https://github.com/sekwah41/Re-Skin/pull/22) ([@sekwah41](https://github.com/sekwah41))

#### ⚠️ Pushed to `release/forge-1.16`

- chore(ci): Upload build files ([@sekwah41](https://github.com/sekwah41))
- chore: Trying to fix new pipelines ([@sekwah41](https://github.com/sekwah41))

#### Authors: 1

- Sekwah ([@sekwah41](https://github.com/sekwah41))

---

## 1.4.0 (2021-06-04)


### ⚠ BREAKING CHANGES

* Specifically relating to if you have command blocks. targeting other entities with /setskin now is /setskin (url) (targets)

### Features

* Added /setmodel command & Reworked /setskin ([01083f1](https://github.com/sekwah41/Re-Skin/commit/01083f13e420ab5f84c1e8bcef230541fc12834c))

### 1.3.3
 * Fixed first person rendering
### 1.3.2
 * Fixed config whitelist and removed spaced from value names.
### 1.3.1
 * Added support for other 1.16 versions.
### 1.3.0
 * Updated to 1.16.2.
 * Added skin server whitelist to protect user's IP's.
### 1.2.0
 * Added the ability to set other players skins with /skin (username) (skinurl)
 * Also fixed issue of missing code which caused skins to not sync when users rejoined the server (that code was left out as it was on my laptop and uncommitted by mistake)
### 1.1.0
 * Added transparent skin support (server config value), fixed a small skin issue and also allowed users to reset their skins with /setskin reset
### 1.0.0
 * Initial release of the mod
