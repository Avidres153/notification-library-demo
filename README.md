# notification-library-demo
Project to create a generic library to send notifications

## 📘 Documentation

- [Quick Start](#-quick-start)
  - [Prerequisites](#-prerequisites)
  - [Instructions to start up the environment](#-instructions-to-start-up-the-environment)
  - [How to execute the example](#-how-to-execute-the-example)
- [Configuration](docs/configuration.md)
- [API Reference](docs/api-reference.md)


## 📋 Quick Start

### ⚙️ Prerequisites
- Java JDK 17
- Maven
- Docker
- PostMan
- Git

### ▶️ Instructions to start up the environment

- Clone the repository
```bash
git clone https://github.com/Avidres153/notification-library-demo.git

cd notification-library-demo
```

- Give execution permissions to the start.sh file
```bash
chmod +x start.sh
```

- Execute the script start.sh
```bash
./start.sh
```

### ▶️ How to execute the example

You can use the postman file to test the example. The file is located in the next path:

```
examples/postman/notification-library-example.postman_collection.json
```

Or If you prefer to create your own request you can follow this steps:

- Create a new `POST` request with this hostname (to change the port value use the `docker-compose.yaml` file): 
```
  http://localhost:8081/notifications/email
```
- Set the body like this: 

```
{
    "to": [
        "example@test.com"
    ],
    "from": "local@test.com",
    "subject": "Email notification",
    "body": "Example to send email notification",
    "attachments": [
        "http://attachment/imag.jpg"
    ]
}
```


