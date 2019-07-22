# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](http://keepachangelog.com/).

## [Unreleased]

## [2.0] - 2018-09-21
### Changed
- Update to Spring Boot `2.1.6.RELEASE`
- Update to influxdb-java `2.15`

## [1.8] - 2018-09-21
### Changed
- Generate meta-data using the annotation processor

## [1.7] - 2018-02-18
### Changed
- Update to Spring Boot `1.5.10.RELEASE`
- Updated to `influxdb-java` version `2.8`

## [1.6] - 2017-08-03
### Added
- Added gzip support
- Added chunking support for queries
- Added possibility to configure  connect, read, and write timeouts
### Changed
- Update to Spring Boot `1.5.3.RELEASE`
- Updated to latest `influxdb-java` version

## [1.5] - 2017-01-18
### Added
- Added Docker Compose configuration to launch a InfluxDB test environment
### Changed
- Updated to latest `influxdb-java` version

## [1.4] - 2016-07-06
### Added
- Added `DefaultInfluxDBTemplate` implementation to simply handle `Point` objects from `influxdb-java`

## [1.3] - 2016-07-06
### Added
- Example application
### Changed
- Refactoring to use a "better" conversion strategy

## [1.2] - 2016-06-14
### Changed
- Fixed typos

## [1.1] - 2016-04-04
### Added
- Documentation and examples
### Changed
- Refactoring and improvements to use the template and its converters

## 1.0 - 2016-04-02
### Added
- Basic Spring Data integration with the official `influxdb-java` library
- Generic template (`InfluxDBTemplate`) to interact with InfluxDB

[Unreleased]: https://github.com/miwurster/spring-data-influxdb/compare/spring-data-influxdb-1.8...HEAD
[2.0]: https://github.com/miwurster/spring-data-influxdb/compare/spring-data-influxdb-1.8...spring-data-influxdb-2.0
[1.8]: https://github.com/miwurster/spring-data-influxdb/compare/spring-data-influxdb-1.7...spring-data-influxdb-1.8
[1.7]: https://github.com/miwurster/spring-data-influxdb/compare/spring-data-influxdb-1.6...spring-data-influxdb-1.7
[1.6]: https://github.com/miwurster/spring-data-influxdb/compare/spring-data-influxdb-1.5...spring-data-influxdb-1.6
[1.5]: https://github.com/miwurster/spring-data-influxdb/compare/spring-data-influxdb-1.4...spring-data-influxdb-1.5
[1.4]: https://github.com/miwurster/spring-data-influxdb/compare/spring-data-influxdb-1.3...spring-data-influxdb-1.4
[1.3]: https://github.com/miwurster/spring-data-influxdb/compare/spring-data-influxdb-1.2...spring-data-influxdb-1.3
[1.2]: https://github.com/miwurster/spring-data-influxdb/compare/spring-data-influxdb-1.1...spring-data-influxdb-1.2
[1.1]: https://github.com/miwurster/spring-data-influxdb/compare/spring-data-influxdb-1.0...spring-data-influxdb-1.1
