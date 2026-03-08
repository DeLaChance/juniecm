# Configuration Management app
A configuration management (CM) application to keep track of software and hardware resources within a company.

## Frontend Layout
It should have a single webpage under `/overview`: 

- with an overview of all configuration items (CI) listed
- it should have a nested table structure, using indentation to 
- the table should be sortable and filterable
- the name column should be editable, the rest is read-only

It should get the list of configuration items from `GET /api/v1/configuration_items/overview`.

## BE layout
Data for the table should be exposed as an api `GET /api/configuration_items`:

This returns a list like:

```
[
    {
        "id": "ABC01",
        "name": "Laptop Lucien",
        "type": "hardware",
        "startDate": "01-03-2026 08:32:01",
        "endDate": null,
        "children": [
            "id": "ABC02",
            "name": "Windows License",
            "type": "software",
            "startDate": "01-03-2026 08:32:01",
            "endDate": null,
        ]
    }
]
```

## Technology
- Use Java 26
- Use Spring Boot version 4.0.3
- Use Maven to build the code
- Use SQLite as a database
- Use Angular 21 for the frontend
- Use Google Material for the UI components

## Domain
- **Configuration Item (CI)**: A piece of hardware or software with a unique id, owner and start and expiry date 
- **Employee**: A person with an employee id and email address
- **Configuration Item Hierarchy**: A Configuration Item (CI) can have children, e.g. a laptop can have software installed onto it.

