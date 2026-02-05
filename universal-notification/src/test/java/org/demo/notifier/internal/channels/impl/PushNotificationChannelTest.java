package org.demo.notifier.internal.channels.impl;

import org.demo.notifier.common.utils.TestUtils;
import org.demo.notifier.internal.model.dto.domain.ChannelConfiguration;
import org.demo.notifier.internal.model.dto.domain.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.domain.NotificationResultDto;
import org.demo.notifier.internal.model.dto.infrastructure.PushNotificationPayload;
import org.demo.notifier.internal.model.enums.ChannelType;
import org.demo.notifier.internal.model.enums.PriorityType;
import org.demo.notifier.internal.service.PushNotificationProvider;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PushNotificationChannelTest {

    @Mock
    private PushNotificationProvider pushNotificationProvider;

    private PushNotificationChannel pushNotificationChannel;

    @Test
    void shouldSendPushNotificationSuccessfully() {
        // Given
        ChannelConfiguration config = TestUtils.configureChannel(true, false);
        pushNotificationChannel = new PushNotificationChannel(config, pushNotificationProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("device-token"), 1, Collections.emptyList(), "local-device", PriorityType.LOW, ChannelType.PUSH_NOTIFICATION);
        NotificationResultDto expectedResult = NotificationResultDto.success("push-sent-id");

        when(pushNotificationProvider.send(any(PushNotificationPayload.class))).thenReturn(expectedResult);

        // When
        NotificationResultDto result = pushNotificationChannel.send(request);

        // Then
        assertNotNull(result);
        assertTrue(result.status());
        assertEquals(expectedResult.externalId(), result.externalId());
        verify(pushNotificationProvider).send(any(PushNotificationPayload.class));
    }

    @Test
    void shouldThrowExceptionWhenAttachmentsArePresent() {
        // Given
        ChannelConfiguration config = TestUtils.configureChannel(false, false);
        pushNotificationChannel = new PushNotificationChannel(config, pushNotificationProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("device-token"), 0, List.of("attachment"), "local-device", PriorityType.LOW, ChannelType.PUSH_NOTIFICATION);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> pushNotificationChannel.send(request));
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("This channel not supports attachments"));
    }

    @Test
    void shouldThrowExceptionWhenRetriesAreNotAllowedButRetriesAreRequested() {
        // Given
        ChannelConfiguration config = TestUtils.configureChannel(false, false);
        pushNotificationChannel = new PushNotificationChannel(config, pushNotificationProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("device-token"), 1, Collections.emptyList(), "local-device", PriorityType.LOW, ChannelType.PUSH_NOTIFICATION);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> pushNotificationChannel.send(request));
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("This channel not supports retries"));
    }

    @Test
    void shouldThrowExceptionWhenRetriesAreAllowedButRetriesNumberIsZero() {
        // Given
        ChannelConfiguration config = TestUtils.configureChannel(true, false);
        pushNotificationChannel = new PushNotificationChannel(config, pushNotificationProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("device-token"), 0, Collections.emptyList(), "local-device", PriorityType.LOW, ChannelType.PUSH_NOTIFICATION);

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> pushNotificationChannel.send(request));
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Retries number must be greater than 0"));
    }

    @Test
    void shouldThrowExceptionWhenPayloadCreationFails() {
        // Given
        ChannelConfiguration config = new ChannelConfiguration.Builder().build();
        pushNotificationChannel = new PushNotificationChannel(config, pushNotificationProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("device-token"), 0, Collections.emptyList(), "local-device", PriorityType.LOW, ChannelType.PUSH_NOTIFICATION);
        String expectedErrorMessage = "Payload creation failed";

        try (MockedStatic<PushNotificationPayload> mockedPayload = Mockito.mockStatic(PushNotificationPayload.class)) {
            mockedPayload.when(() -> PushNotificationPayload.fromRequestRecord(request)).thenThrow(new RuntimeException(expectedErrorMessage));

            // When & Then
            RuntimeException exception = assertThrows(RuntimeException.class, () -> pushNotificationChannel.send(request));
            assertNotNull(exception);
            assertTrue(exception.getMessage().contains(expectedErrorMessage));
        }
    }

    @Test
    void shouldThrowExceptionWhenProviderFails() {
        // Given
        ChannelConfiguration config = new ChannelConfiguration.Builder().build();
        pushNotificationChannel = new PushNotificationChannel(config, pushNotificationProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("device-token"), 0, Collections.emptyList(), "local-device", PriorityType.LOW, ChannelType.PUSH_NOTIFICATION);
        String expectedErrorMessage = "Push provider failed";

        when(pushNotificationProvider.send(any(PushNotificationPayload.class))).thenThrow(new RuntimeException(expectedErrorMessage));

        // When & Then
        RuntimeException exception = assertThrows(RuntimeException.class, () -> pushNotificationChannel.send(request));
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains(expectedErrorMessage));
    }
}
