<!-- Improved compatibility of back to top link: See: https://github.com/othneildrew/Best-README-Template/pull/73 -->

<a name="readme-top"></a>

<!--
*** Thanks for checking out the Best-README-Template. If you have a suggestion
*** that would make this better, please fork the repo and create a pull request
*** or simply open an issue with the tag "enhancement".
*** Don't forget to give the project a star!
*** Thanks again! Now go create something AMAZING! :D
-->

<!-- PROJECT SHIELDS -->

<!--
*** I'm using markdown "reference style" links for readability.
*** Reference links are enclosed in brackets [ ] instead of parentheses ( ).
*** See the bottom of this document for the declaration of the reference variables
*** for contributors-url, forks-url, etc. This is an optional, concise syntax you may use.
*** https://www.markdownguide.org/basic-syntax/#reference-style-links
-->

<!-- PROJECT LOGO -->

<br />
<div align="center">
    <img src="./static/logo.svg" alt="Logo" width="80" height="80">
  <h3 align="center">Beeve</h3>

  <p align="center">The ultimate application for wine production management</p>
</div>

<!-- TABLE OF CONTENTS -->

<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#configuration">Configuration</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>

<!-- ABOUT THE PROJECT -->

## About The Project

Application created to facilitate the management of a wine cellar. <br>Aims to solve problems related to managing production processes, contact management, and many more, by providing intuitive and functional APIs

<!-- GETTING STARTED -->

## Getting Started

### Prerequisites :vertical\_traffic\_light:

*   Docker

### Configuration :hammer:

1.  Create a `.env` file under `/vino`. Follow the `.env_example` and fill in the fields with values of your choice, many of which refer to the credentials you will use for the database

2.  For the ***JWT\_SECRET*** in the `.env` file you just created be careful to use a _**hexadecimal**_ (at minumum 256 bits) encryption key. For that matter i invite you to generate it. Here is what i use https://www.allkeysgenerator.com/ <br>(be sure to switch from GUID to Encryption key section, and tick the "HEX" box)

3.  Modify the `application.yml` (which is under `src/main/resources`) by filling the `username` and `password` fields, remembering to use the same credentials that you set in the `.env` file

4.  Also in the `application.yml` under `datasource` change the url by replacing the database name with the one chosen in the `.env` file. <br>For example, if you chose to call the database `foo` then the url should look like this<br>
    `jdbc:mariadb://vino-db:3306/foo`

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- USAGE EXAMPLES -->

## Usage :wine\_glass:

Assuming that the configuration has been done correctly, is now possible to start the application by running<br> `docker-compose up -d --build`

The project will be running on port `8080` unless you change it in the `application.properties` file.<br><br>

:warning:<br>
**Remember to wait until the container has completed initialisation before using the application. You can check the status of the container by running the command**<br> `docker logs vino-backend`

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Roadmap

- [x] Full applicative Logic
- [x] DTT storage
- [ ] Push notifications
- [ ] Statistics

See the [open issues](https://github.com/lordimens/vino_backend/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- CONTACT -->

## Contact :scroll:

#### Managers

Lorenzo Di Menna - l.dimenna1@studenti.unimol.it<br>
Giulia Guarino - g.guarino3@studenti.unimol.it<br>
Alessio Del Riccio - a.delriccio1@studenti.unimol.it<br>

#### Developers

Marco Omicini - m.omicini1@studenti.unimol.it<br>
Christian D'alleva - c.dalleva@studenti.unimol.it<br>
Mario Autore - marioautore8@gmail.com<br>
Angelo Conca -  angelo.conca99@gmail.com<br>
Mattia Natale -  mattia.natale225@gmail.com<br>
Giuseppe Di Menna - giuseppedim@outlook.it<br>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- ACKNOWLEDGMENTS -->

## Acknowledgments

*   [SpringBoot](https://github.com/spring-projects/spring-boot)
*   [Maven](https://github.com/apache/maven)
*   [JUnit](https://github.com/junit-team/junit4)

<p align="right">(<a href="#readme-top">back to top</a>)</p>
