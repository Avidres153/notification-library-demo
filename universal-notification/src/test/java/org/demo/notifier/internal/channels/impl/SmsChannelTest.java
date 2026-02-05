package org.demo.notifier.internal.channels.impl;

import org.demo.notifier.common.utils.TestUtils;
import org.demo.notifier.internal.model.dto.domain.ChannelConfiguration;
import org.demo.notifier.internal.model.dto.domain.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.domain.NotificationResultDto;
import org.demo.notifier.internal.model.dto.infrastructure.SmsPayloadDto;
import org.demo.notifier.internal.model.enums.PriorityType;
import org.demo.notifier.internal.service.SmsProvider;
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
public class SmsChannelTest {

    @Mock
    private SmsProvider smsProvider;

    private SmsChannel smsChannel;

    @Test
    void shouldSendSmsSuccessfully() {
        // Given
        ChannelConfiguration config = TestUtils.configureChannel(true, false);
        smsChannel = new SmsChannel(config, smsProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("1234567890"), 1, Collections.emptyList(), "0987654", PriorityType.LOW);
        NotificationResultDto expectedResult = NotificationResultDto.success("sms-sent-id");

        when(smsProvider.send(any(SmsPayloadDto.class))).thenReturn(expectedResult);

        NotificationResultDto result = smsChannel.send(request);

        assertNotNull(result);
        assertTrue(result.status());
        assertEquals(expectedResult.externalId(), result.externalId());
        verify(smsProvider).send(any(SmsPayloadDto.class));
    }

    @Test
    void shouldThrowExceptionWhenAttachmentsArePresent() {
        // Given
        ChannelConfiguration config = TestUtils.configureChannel(false, false);
        smsChannel = new SmsChannel(config, smsProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("1234567890"), 0, List.of("attachment"), "0987654", PriorityType.LOW);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> smsChannel.send(request));
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("This channel not supports attachments"));
    }

    @Test
    void shouldThrowExceptionWhenRetriesAreNotAllowedButRetriesAreRequested() {
        // Given
        ChannelConfiguration config = TestUtils.configureChannel(false, false);
        smsChannel = new SmsChannel(config, smsProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("1234567890"), 1, Collections.emptyList(), "0987654", PriorityType.LOW);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> smsChannel.send(request));
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("This channel not supports retries"));
    }

    @Test
    void shouldThrowExceptionWhenRetriesAreAllowedButRetriesNumberIsZero() {
        // Given
        ChannelConfiguration config = TestUtils.configureChannel(true, false);
        smsChannel = new SmsChannel(config, smsProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("1234567890"), 0, Collections.emptyList(), "0987654", PriorityType.LOW);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> smsChannel.send(request));
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Retries number must be greater than 0"));
    }

    @Test
    void shouldThrowExceptionWhenPayloadCreationFails() {
        // Given
        ChannelConfiguration config = new ChannelConfiguration.Builder().build();
        smsChannel = new SmsChannel(config, smsProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("1234567890"), 0, Collections.emptyList(), "0987654", PriorityType.LOW);
        String expectedErrorMessage = "Payload creation failed";

        try (MockedStatic<SmsPayloadDto> mockedPayload = Mockito.mockStatic(SmsPayloadDto.class)) {
            mockedPayload.when(() -> SmsPayloadDto.fromRequestRecord(request)).thenThrow(new RuntimeException(expectedErrorMessage));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> smsChannel.send(request));
            assertNotNull(exception);
            assertTrue(exception.getMessage().contains(expectedErrorMessage));
        }
    }

    @Test
    void shouldThrowExceptionWhenProviderFails() {
        // Given
        ChannelConfiguration config = new ChannelConfiguration.Builder().build();
        smsChannel = new SmsChannel(config, smsProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("1234567890"), 0, Collections.emptyList(), "0987654", PriorityType.LOW);
        String expectedErrorMessage = "SMS provider failed";

        when(smsProvider.send(any(SmsPayloadDto.class))).thenThrow(new RuntimeException(expectedErrorMessage));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> smsChannel.send(request));
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains(expectedErrorMessage));
    }
}
