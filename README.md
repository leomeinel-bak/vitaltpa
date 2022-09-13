<!-- PROJECT SHIELDS -->

[![Contributors][contributors-shield]][contributors-url]
[![Forks][forks-shield]][forks-url]
[![Stargazers][stars-shield]][stars-url]
[![Issues][issues-shield]][issues-url]
[![MIT License][license-shield]][license-url]
[![Quality][quality-shield]][quality-url]

<!-- PROJECT LOGO -->
<!--suppress ALL -->
<br />
<p align="center">
  <a href="https://github.com/LeoMeinel/VitalTpa">
    <img src="images/logo.png" alt="Logo" width="80" height="80">
  </a>

<h3 align="center">VitalTpa</h3>

  <p align="center">
    Ask for teleport on Spigot and Paper
    <br />
    <a href="https://github.com/LeoMeinel/VitalTpa"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/LeoMeinel/VitalTpa">View Demo</a>
    ·
    <a href="https://github.com/LeoMeinel/VitalTpa/issues">Report Bug</a>
    ·
    <a href="https://github.com/LeoMeinel/VitalTpa/issues">Request Feature</a>
  </p>

<!-- ABOUT THE PROJECT -->

## About The Project

### Description

VitalTpa is a Plugin that gives players the ability to ask players to teleport to them.

This plugin is perfect for any server wanting their players to have an easy way of sending teleport requests.

### Features

- Send request to teleport to others
- Send request to teleport others to you

### Built With

- [Gradle 7](https://docs.gradle.org/7.5.1/release-notes.html)
- [OpenJDK 17](https://openjdk.java.net/projects/jdk/17/)

<!-- GETTING STARTED -->

## Getting Started

To get the plugin running on your server follow these simple steps.

### Commands and Permissions

1. Permission: `vitaltpa.tpa`

- Command: `/tpa <player>`
- Description: Send request to teleport to others

2. Permission: `vitaltpa.tpahere`

- Command: `/tpahere <player>`
- Description: Send request to teleport others to you

3. Permission: `vitaltpa.tpyes`

- Command: `/tpyes`
- Description: Accept teleport request

4. Permission: `vitaltpa.tpno`

- Command: `/tpno`
- Description: Deny teleport request

4. Permission: `vitaltpa.tpcancel`

- Description: Cancel request

5. Permission: `vitaltpa.delay.bypass`

- Description: Bypass delay

### Configuration - config.yml

```yaml
# Command delay
delay:
  enabled: true
  # time in s
  time: 3

# time in seconds
request-expiry: 60
```

### Configuration - messages.yml

```yaml
cmd: "&fUsage: &b/tpa <player> &for &b/tpahere <player> &for &b/tpyes &for &b/tpno"
tpa-sent: "&b%player% &fhas been sent a request"
no-request: "&cNo active request!"
tpa-no: "&fYou denied the request from &b%player%"
tpa-denied: "&b%player% &fdenied your request"
tpa-yes: "&fYou accepted the request from &b%player%"
tpa-accepted: "&b%player% &faccepted your request"
tpa-cancelled: "&b%player% &ccancelled the request!"
no-perms: "&cYou don't have enough permissions!"
player-only: "&cThis command can only be executed by players!"
not-online: "&cPlayer is not online!"
same-player: "&cYou can't send a request to yourself!"
active-tpa: "&cYou already have an active teleport request"
tpa-received: "&b%player% &fwants to teleport to you"
tpahere-received: "&b%player% &fwants you to teleport to them"
countdown: "&fTeleporting in &b%countdown% &fseconds"
active-delay: "&cYou already accepted the request!"
```

<!-- ROADMAP -->

## Roadmap

See the [open issues](https://github.com/LeoMeinel/VitalTpa/issues) for a list of proposed features (and known
issues).

<!-- CONTRIBUTING -->

## Contributing

Contributions are what make the open source community such an amazing place to be, learn, inspire, and create. Any
contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<!-- LICENSE -->

## License

Distributed under the GNU General Public License v3.0. See `LICENSE` for more information.

<!-- CONTACT -->

## Contact

Leopold Meinel - [leo@meinel.dev](mailto:leo@meinel.dev) - eMail

Project Link - [VitalTpa](https://github.com/LeoMeinel/VitalTpa) - GitHub

<!-- ACKNOWLEDGEMENTS -->

### Acknowledgements

- [README.md - othneildrew](https://github.com/othneildrew/Best-README-Template)

<!-- MARKDOWN LINKS & IMAGES -->

[contributors-shield]: https://img.shields.io/github/contributors-anon/LeoMeinel/VitalTpa?style=for-the-badge
[contributors-url]: https://github.com/LeoMeinel/VitalTpa/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/LeoMeinel/VitalTpa?label=Forks&style=for-the-badge
[forks-url]: https://github.com/LeoMeinel/VitalTpa/network/members
[stars-shield]: https://img.shields.io/github/stars/LeoMeinel/VitalTpa?style=for-the-badge
[stars-url]: https://github.com/LeoMeinel/VitalTpa/stargazers
[issues-shield]: https://img.shields.io/github/issues/LeoMeinel/VitalTpa?style=for-the-badge
[issues-url]: https://github.com/LeoMeinel/VitalTpa/issues
[license-shield]: https://img.shields.io/github/license/LeoMeinel/VitalTpa?style=for-the-badge
[license-url]: https://github.com/LeoMeinel/VitalTpa/blob/main/LICENSE
[quality-shield]: https://img.shields.io/codefactor/grade/github/LeoMeinel/VitalTpa?style=for-the-badge
[quality-url]: https://www.codefactor.io/repository/github/LeoMeinel/VitalTpa
