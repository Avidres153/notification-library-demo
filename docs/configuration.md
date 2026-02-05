### ▶️ Channel Configuration
In case more channels are required than those currently existing, the following steps must be followed:

- Create a Java class that implements the `NotificationChannel` interface.
- Override the `send` method.
  - Perform validations of the `DeliveryRequestRecord` request in conjunction with the configuration policies for the channel being created.
  - A `Payload` object must be created in the `model.dto.infrastructure` package based on the needs of the channel and the provider. This object is what the provider will use to send the notification.
  - Create an interface for the channel's notification provider. This interface must have the following signature (Ex: EmailProvider):
  ```
  NotificationResultDto send(EmailPayloadDto emailPayloadDto);
  ```
  `Note: The Payload used for the new channel must be used.`


### ▶️ Provider Configuration

Providers must be created on the user side using the interface. To do this, the following steps must be followed:

- Create a class that implements the provider interface created in the previous step (Ex: EmailProvider). This class must be created in the package: `service`.
- Override the `send` method to configure the provider (tokens, credentials, etc.).
