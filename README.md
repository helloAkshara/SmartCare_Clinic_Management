# SmartCare – Clinic Management & Rule-Based Triage System

## Project Overview

SmartCare is a Java-based clinic management system that helps manage patients, doctors, appointments, medical records, and patient history. It also includes an educational rule-based triage system to classify cases as Low, Medium, or High priority.

> **Note:** This project is for educational purposes and does not provide medical diagnosis.

## Features

- Patient registration and management
- Doctor registration and management
- Symptom-based triage
- Appointment booking
- Medical recocards
- Patient history
- Daily reports
- Input validation and error handling
- File-based data storage

## Project Modules

The project is divided into multiple Java classes for better organization and maintainability:

## Project Modules

| Module / Class | Description |
|---|---|
| `Patient` | Manages patient information such as ID, name, age, contact, and medical records. |
| `Doctor` | Manages doctor information, specialization, and available appointment slots. |
| `Symptom` | Stores symptom names and their predefined base scores. |
| `TriageEngine` | Calculates the triage score and classifies cases as Low, Medium, or High priority. |
| `TriageResult` | Stores and displays the triage priority, score, and reason. |
| `Appointment` | Manages appointment details such as patient, doctor, date, time, and status. |
| `MedicalRecord` | Stores patient symptoms, doctor notes, prescription, and follow-up details. |
| `ClinicManager` | Coordinates patients, doctors, appointments, triage, and overall clinic operations. |
| `InputValidator` | Validates user inputs such as age, dates, choices, and empty fields. |
| `FileManager` | Handles saving patient, doctor, and appointment data to text files. |
| `ReportGenerator` | Generates daily clinic reports and priority statistics. |
| `Main` | Contains the main application flow, menu, and user interaction. |

## Technologies / Tools

- Java
- IntelliJ IDEA
- Git
- GitHub
- Text File Storage

## Installation & Running

1. Install Java JDK 8 or above.
2. Clone the GitHub repository.
3. Open the project in IntelliJ IDEA.
4. Locate the `Main` class inside the `smartcare` package.
5. Run the `Main` class.
6. Follow the menu displayed in the console.

## Testing

Test the following functions:

- Register and search patients
- Register and view doctors
- Perform triage with different symptoms and severity levels
- Book appointments
- Add and view medical records
- Generate daily reports
- Test invalid inputs
- Test occupied or unavailable appointment slots

## Project Disclaimer

The triage system uses predefined educational rules to classify cases into Low, Medium, or High priority. These rules are implemented only for demonstrating Java programming concepts and should not be used for real medical decisions.