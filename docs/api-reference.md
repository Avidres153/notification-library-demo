### ▶️ Exposed methods and API usage

Before sending the notification, the user must configure the following:

* `Provider`: 
  Providers must be created on the user side using the interface. To do this, the following steps must be followed:

  - Create a class that implements the provider interface created in the previous step (Ex: EmailProvider). This class must be created in the package: `service`.
  - Override the `send` method to configure the provider (tokens, credentials, etc.).

```
EmailProvider emailProvider = new SendGridEmailProvider();
```

* `Channel`: 
  The channel configuration is used to specify if it will support retries and attachments. To do this, it is necessary to add an object like this:

```
ChannelConfiguration emailConfig =
                new ChannelConfiguration.Builder()
                        .allowAttachments(true)
                        .allowRetries(true)
                        .build();
```

* `Client`: 

The client is the object that will allow us to specify one or several channels to be used for sending the notification. Ex:
```
NotificationServiceClient client =
                NotificationClientFactory.create(
                        Map.of(ChannelType.EMAIL, emailChannel)
                );
```

* `Request`:

This object will contain all the information to send the message. For this, a record composed of several objects necessary for sending is used. It is important to mention that the type of channel to be used must be specified so that the notification can be sent through the correct channel. Here is an example of creating a request:

```
NotificationDto notificationDto = new NotificationDto.Builder().setId("123").setNotificationContent(notificationContentDto).build();
DestinationDto destinationDto = new DestinationDto.Builder().to(List.of("local-server@test.com")).build();
DigitalDeliveryPoliciesDto deliveryPoliciesDto =  new DigitalDeliveryPoliciesDto.Builder().setPriority(PriorityType.HIGH).build();
DeliveryRequestRecord deliveryRequestRecord = new DeliveryRequestRecord(notificationDto, destinationDto, deliveryPoliciesDto, ChannelType.EMAIL);
```

Finally, to send the notification, the `send` method of the previously configured client must be invoked as follows:

```
NotificationResultDto resultDto = client.send(deliveryRequestRecord);
```
