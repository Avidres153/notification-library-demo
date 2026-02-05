package org.demo.notifier.internal.channels.impl;

import org.demo.notifier.common.utils.TestUtils;
import org.demo.notifier.internal.model.dto.domain.ChannelConfiguration;
import org.demo.notifier.internal.model.dto.domain.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.domain.NotificationResultDto;
import org.demo.notifier.internal.model.dto.infrastructure.EmailPayloadDto;
import org.demo.notifier.internal.model.enums.ChannelType;
import org.demo.notifier.internal.model.enums.PriorityType;
import org.demo.notifier.internal.service.EmailProvider;
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
public class EmailChannelTest {

    @Mock
    private EmailProvider emailProvider;

    private EmailChannel emailChannel;


    @Test
    void shouldSendEmailSuccessfully() {
        // Given
        ChannelConfiguration config = TestUtils.configureChannel(true, true);
        emailChannel = new EmailChannel(config, emailProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("test@test.com"), 1, Collections.emptyList(), "local-server@test.com", PriorityType.LOW, ChannelType.EMAIL);
        NotificationResultDto expectedResult = NotificationResultDto.success("id-001");

        when(emailProvider.send(any(EmailPayloadDto.class))).thenReturn(expectedResult);

        NotificationResultDto result = emailChannel.send(request);

        assertNotNull(result);
        assertTrue(result.status());
        assertEquals(expectedResult.externalId(), result.externalId());
        verify(emailProvider).send(any(EmailPayloadDto.class));
    }

    @Test
    void shouldThrowExceptionWhenAttachmentsAreNotAllowed() {
        // Given
        ChannelConfiguration config = TestUtils.configureChannel(false, false);
        emailChannel = new EmailChannel(config, emailProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("test@test.com"), 0, List.of("attachment"), "local-server@test.com", PriorityType.LOW, ChannelType.EMAIL);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> emailChannel.send(request));
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("This channel not supports attachments"));
    }

    @Test
    void shouldThrowExceptionWhenRetriesAreNotAllowedButRetriesAreRequested() {
        // Given
        ChannelConfiguration config = TestUtils.configureChannel(false, false);
        emailChannel = new EmailChannel(config, emailProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("test@test.com"), 1, Collections.emptyList(), "local-server@test.com", PriorityType.LOW, ChannelType.EMAIL);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> emailChannel.send(request));
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("This channel not supports retries"));
    }

    @Test
    void shouldThrowExceptionWhenRetriesAreAllowedButRetriesNumberIsZero() {
        // Given
        ChannelConfiguration config = TestUtils.configureChannel(true, false);
        emailChannel = new EmailChannel(config, emailProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("test@test.com"), 0, Collections.emptyList(), "local-server@test.com", PriorityType.LOW, ChannelType.EMAIL);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> emailChannel.send(request));
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains("Retries number must be greater than 0"));
    }

    @Test
    void shouldThrowExceptionWhenPayloadCreationFails() {
        // Given
        ChannelConfiguration config = new ChannelConfiguration.Builder().build();
        emailChannel = new EmailChannel(config, emailProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("test@test.com"), 0, Collections.emptyList(), "local-server@test.com", PriorityType.LOW, ChannelType.EMAIL);
        String expectedErrorMessage = "Payload creation failed";

        try (MockedStatic<EmailPayloadDto> mockedPayload = Mockito.mockStatic(EmailPayloadDto.class)) {
            mockedPayload.when(() -> EmailPayloadDto.fromRequestRecord(request)).thenThrow(new RuntimeException(expectedErrorMessage));

            RuntimeException exception = assertThrows(RuntimeException.class, () -> emailChannel.send(request));
            assertNotNull(exception);
            assertTrue(exception.getMessage().contains(expectedErrorMessage));
        }
    }

    @Test
    void shouldThrowExceptionWhenProviderFails() {
        // Given
        ChannelConfiguration config = new ChannelConfiguration.Builder().build();
        emailChannel = new EmailChannel(config, emailProvider);
        DeliveryRequestRecord request = TestUtils.createGenericRequest(List.of("test@test.com"), 0, Collections.emptyList(), "local-server@test.com", PriorityType.LOW, ChannelType.EMAIL);
        String expectedErrorMessage = "Email provider failed";

        when(emailProvider.send(any(EmailPayloadDto.class))).thenThrow(new RuntimeException(expectedErrorMessage));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> emailChannel.send(request));
        assertNotNull(exception);
        assertTrue(exception.getMessage().contains(expectedErrorMessage));
    }
}