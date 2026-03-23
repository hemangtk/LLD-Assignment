# Multi-Level Parking Lot

An object-oriented implementation of a multi-level parking lot system in Java. The design maps physical entities into a software architecture utilizing SOLID principles.

## System Design & Architecture

* **Strategy Pattern:** The system employs a `SlotAssignmentStrategy` to locate the nearest available slot through 3D Euclidean distance calculations and a `PricingStrategy` to determine hourly charges. This approach ensures the core `ParkingLot` logic remains highly extensible without modification.

* **Nearest Slot Assignment:** The `NearestSlotStrategy` finds the closest available slot to the entry gate using Euclidean distance across floors, ensuring vehicles park as close to their entry point as possible.

* **Ticket Tracking:** Active tickets are tracked via a `Set<String>` to prevent double exits. Each ticket is validated before processing an exit.

* **Single Responsibility:** Core domain objects (`Vehicle`, `Slot`, `Gate`, `Ticket`) each own their specific state. The `ParkingLot` orchestrates the three APIs — `park()`, `status()`, and `exit()`.

## Class Diagram

![Parking Lot UML](multi-lvl.png)

## Core Entities

| Class | Responsibility |
|-------|---------------|
| `ParkingLot` | Central orchestrator — manages slots, gates, active tickets, and exposes `park()`, `status()`, `exit()` APIs. |
| `Slot` | Parking space with floor, coordinates, and occupancy state. Calculates 3D distance to a gate. |
| `Gate` | Entry point with floor and (x, y) coordinates for distance calculation. |
| `Vehicle` | License plate and vehicle type (TWO_WHEELER, CAR, BUS). |
| `Ticket` | Generated on park — holds vehicle, assigned slot, and entry time. |
| `VehicleType` | Enum: TWO_WHEELER, CAR, BUS. |
| `SlotAssignmentStrategy` | Interface for slot selection logic. |
| `NearestSlotStrategy` | Finds the nearest available slot using 3D Euclidean distance. |
| `PricingStrategy` | Interface for fee calculation. |
| `HourlyPricingStrategy` | Calculates fee = ceil(hours) x hourly rate. |

## APIs

| Method | Description |
|--------|-------------|
| `park(vehicle, entryTime, entryGateId)` | Parks a vehicle in the nearest available slot, returns `Ticket`. |
| `status()` | Prints available vs total slot counts. |
| `exit(ticket, exitTime)` | Validates ticket, frees the slot, returns bill amount. |

## How to Run

```bash
cd src
javac *.java
java Main
```
