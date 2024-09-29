# Fenix Commerce

## Features

- **Multi-Tenant Syncing**: Sync historical data for multiple tenants. We can specify a shop URL or fetch details from application properties or a database table.
- **Elasticsearch Integration**: Store and query data using Elasticsearch for efficient and scalable search.
- **Configurable Modes**: Choose to sync all tenants or just specific ones.
- **Shopify Webhook**: Automatically receive updates for new orders and fulfillments via webhooks from Shopify.

## Technologies

- **Java**: Version 17
- **Spring Boot**: Version 3.x
- **Elasticsearch**: Version 8.x

## Installation

### Prerequisites

- Java 17 or later
- Maven
- Elasticsearch 8.x (running locally or remotely)

### Steps to Install

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/vipul96/fenix-commerce-poc.git
   cd fenix-commerce-poc
2. **Build the Project**:
    ```bash
    mvn clean install

### Running the Application

Once the application is running, you can query the synced data directly from Elasticsearch using standard queries.

### Elasticsearch Setup

Make sure Elasticsearch is running. You can check its status by visiting [http://localhost:9200](http://localhost:9200).

### Index Creation

The necessary indices for orders and fulfillments will be created automatically when you run the application, if they do not already exist.

### Usage

You can configure the application to sync data for specific tenants or all tenants as needed. Monitor the logs for information on the syncing process and any webhook triggers from Shopify.




