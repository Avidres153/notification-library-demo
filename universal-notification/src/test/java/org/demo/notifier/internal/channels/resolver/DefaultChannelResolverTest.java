package org.demo.notifier.internal.channels.resolver;

import org.demo.notifier.internal.model.enums.ChannelType;
import org.demo.notifier.internal.service.NotificationChannel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class DefaultChannelResolverTest {

    @Mock
    private NotificationChannel emailChannel;

    @Mock
    private NotificationChannel smsChannel;

    @Mock
    private NotificationChannel pushChannel;

    private DefaultChannelResolver defaultChannelResolver;
    private Map<ChannelType, NotificationChannel> channelMap;

    @BeforeEach
    void setUp() {
        channelMap = new HashMap<>();
        channelMap.put(ChannelType.EMAIL, emailChannel);
        channelMap.put(ChannelType.SMS, smsChannel);
        channelMap.put(ChannelType.PUSH_NOTIFICATION, pushChannel);
        
        defaultChannelResolver = new DefaultChannelResolver(channelMap);
    }

    @Test
    void shouldResolveEmailChannelSuccessfully() {
        NotificationChannel result = defaultChannelResolver.resolve(ChannelType.EMAIL);

        assertNotNull(result);
        assertEquals(emailChannel, result);
    }

    @Test
    void shouldResolveSmsChannelSuccessfully() {
        NotificationChannel result = defaultChannelResolver.resolve(ChannelType.SMS);

        assertNotNull(result);
        assertEquals(smsChannel, result);
    }

    @Test
    void shouldResolvePushNotificationChannelSuccessfully() {
        NotificationChannel result = defaultChannelResolver.resolve(ChannelType.PUSH_NOTIFICATION);

        assertNotNull(result);
        assertEquals(pushChannel, result);
    }

    @Test
    void shouldThrowExceptionWhenChannelNotFound() {
        Map<ChannelType, NotificationChannel> limitedMap = new HashMap<>();
        limitedMap.put(ChannelType.EMAIL, emailChannel);
        DefaultChannelResolver limitedResolver = new DefaultChannelResolver(limitedMap);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> limitedResolver.resolve(ChannelType.SMS));
        assertEquals("Channel not found", exception.getMessage());
    }
    
    @Test
    void shouldThrowExceptionWhenChannelTypeIsNull() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> defaultChannelResolver.resolve(null));
        assertEquals("Channel type could not be null", exception.getMessage());
    }
}
