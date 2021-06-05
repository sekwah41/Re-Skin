# Changelog

All notable changes to this project will be documented in this file. See [standard-version](https://github.com/conventional-changelog/standard-version) for commit guidelines.

For the release changelogs see [CHANGELOG.md](CHANGELOG.md)  
For the snapshot changelogs see [SNAPSHOT_CHANGELOG.md](SNAPSHOT_CHANGELOG.md)

### [1.4.1-0](https://github.com/sekwah41/Re-Skin/compare/v1.4.0...v1.4.1-0) (2021-06-05)


### Bug Fixes

* Client render errors with /clearskin ([4642527](https://github.com/sekwah41/Re-Skin/commit/4642527cda891e715196b95825cc5304f0e21d9d))
* Command arg types causing network errors ([2057d6d](https://github.com/sekwah41/Re-Skin/commit/2057d6d3cb25a92e24d6df3fe0631f4976de60d6))

## 1.4.0-0 (2021-06-03)


### ⚠ BREAKING CHANGES

* Specifically relating to if you have command blocks. targeting other entities with /setskin now is /setskin (url) (targets)

### Features

* Added /setmodel command & Reworked /setskin ([01083f1](https://github.com/sekwah41/Re-Skin/commit/01083f13e420ab5f84c1e8bcef230541fc12834c))

### 1.3.4-0 (2021-05-11)
 * First test version of new build process
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
