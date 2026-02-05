package org.demo.notifier.api.internal.dispatcher;

import org.demo.notifier.common.utils.TestUtils;
import org.demo.notifier.internal.channels.impl.EmailChannel;
import org.demo.notifier.internal.dispatcher.DefaultNotificationDispatcher;
import org.demo.notifier.internal.model.dto.domain.ChannelConfiguration;
import org.demo.notifier.internal.model.dto.domain.DeliveryRequestRecord;
import org.demo.notifier.internal.model.dto.domain.DestinationDto;
import org.demo.notifier.internal.model.dto.domain.DigitalDeliveryPoliciesDto;
import org.demo.notifier.internal.model.dto.domain.NotificationContentDto;
import org.demo.notifier.internal.model.dto.domain.NotificationDto;
import org.demo.notifier.internal.model.dto.domain.NotificationResultDto;
import org.demo.notifier.internal.model.dto.infrastructure.EmailPayloadDto;
import org.demo.notifier.internal.model.enums.ChannelType;
import org.demo.notifier.internal.model.enums.PriorityType;
import org.demo.notifier.internal.service.ChannelResolver;
import org.demo.notifier.internal.service.EmailProvider;
import org.demo.notifier.internal.service.NotificationChannel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DefaultNotificationDispatcherTest {

    @Mock
    private ChannelResolver channelResolver;

    @Mock
    private EmailProvider emailProvider;

    @InjectMocks
    private DefaultNotificationDispatcher dispatcher;


    @Test
    void shouldSendNotificationSuccessfullyWithoutRetries() {
        // given
        List<String> toList = new ArrayList<>();
        toList.add("email@test.com");
        DeliveryRequestRecord request = TestUtils.createGenericRequest(toList,0,new ArrayList<>(),"local-server@test.com", PriorityType.LOW, ChannelType.EMAIL);
        NotificationResultDto success = NotificationResultDto.success("id-1");

        // channel configuration
        ChannelConfiguration emailConfig = TestUtils.configureChannel(false, false);

        NotificationChannel emailChannel =
                new EmailChannel(emailConfig, emailProvider);

        when(channelResolver.resolve(ChannelType.EMAIL)).thenReturn(emailChannel);
        when(emailProvider.send(any(EmailPayloadDto.class))).thenReturn(success);


        NotificationResultDto result = dispatcher.dispatch(request);


        assertTrue(result.status());
        verify(emailProvider, times(1)).send(any(EmailPayloadDto.class));
    }

    @Test
    void shouldRetryAndSucceedBeforeMaxRetries() {
        // given
        List<String> toList = new ArrayList<>();
        toList.add("email@test.com");
        DeliveryRequestRecord request = TestUtils.createGenericRequest(toList,3,new ArrayList<>(),"local-server@test.com", PriorityType.LOW, ChannelType.EMAIL);

        // channel configuration
        ChannelConfiguration emailConfig = TestUtils.configureChannel(true, false);

        NotificationChannel emailChannel =
                new EmailChannel(emailConfig, emailProvider);

        when(channelResolver.resolve(ChannelType.EMAIL)).thenReturn(emailChannel);
        when(emailProvider.send(any(EmailPayloadDto.class)))
                .thenReturn(NotificationResultDto.failure("1", "error 1"))
                .thenReturn(NotificationResultDto.failure("2", "error 2"))
                .thenReturn(NotificationResultDto.success("3"));


        NotificationResultDto result = dispatcher.dispatch(request);


        assertTrue(result.status());
        verify(emailProvider, times(3)).send(any(EmailPayloadDto.class));
    }

}
