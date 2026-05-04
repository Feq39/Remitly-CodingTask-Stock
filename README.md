# Simplified Stock Market

A Java Spring Boot service that simulates a simplified stock market.

## Requirements

- Docker

## Run

Linux / macOS:

```bash
./run.sh <port>
```

Windows PowerShell:

```powershell
./run.ps1 <port>
```

Example:

```bash
./run.sh 8080
```

The service will be available at:

```text
http://localhost:<port>
```

## Stop

```bash
docker compose down
```

## API

### Buy or sell one stock

```http
POST /wallets/{wallet_id}/stocks/{stock_name}
```

Body:

```json
{
  "type": "buy"
}
```

or:

```json
{
  "type": "sell"
}
```

Creates the wallet if it does not exist.

Responses:

- `200` when the operation succeeds
- `400` when the operation cannot be completed
- `404` when the stock does not exist

### Get wallet

```http
GET /wallets/{wallet_id}
```

Response:

```json
{
  "id": "wallet1",
  "stocks": [
    {
      "name": "stock1",
      "quantity": 99
    }
  ]
}
```

### Get wallet stock quantity

```http
GET /wallets/{wallet_id}/stocks/{stock_name}
```

Response:

```json
99
```

### Get bank stocks

```http
GET /stocks
```

Response:

```json
{
  "stocks": [
    {
      "name": "stock1",
      "quantity": 99
    }
  ]
}
```

### Set bank stocks

```http
POST /stocks
```

Body:

```json
{
  "stocks": [
    {
      "name": "stock1",
      "quantity": 99
    }
  ]
}
```

### Get audit log

```http
GET /log
```

Response:

```json
{
  "log": [
    {
      "type": "buy",
      "wallet_id": "wallet1",
      "stock_name": "stock1"
    }
  ]
}
```

Only successful wallet operations are logged.

### Kill current instance

```http
POST /chaos
```

Kills the instance that serves the request.

## Assumptions

- Stock price is always `1`.
- Wallet balance is not tracked.
- Buy and sell operations are executed immediately.
- The bank is the only liquidity provider.
- Initially there are no wallets and the bank is empty.
- The service is highly available, so killing one instance does not stop the product.
