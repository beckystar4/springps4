package com.example.springps4.mapper;

import com.example.springps4.model.response.PublisherResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PublisherMapperTest {

    @InjectMocks
    PublisherMapper mapper;

    @Mock
    ResultSet rs;

    @Test
    void mapRow() throws SQLException {
        // Arrange: Set up mock ResultSet behavior
        when(rs.getLong("publisher_id")).thenReturn(1L);  // Mock publisher_id as a long
        when(rs.getString("publisher")).thenReturn("Rockstar");  // Mock publisher as a string
        when(rs.getInt("game_id")).thenReturn(81);  // Mock game_id as an int

        // Act: Call the method to test
        PublisherResponse result = mapper.mapRow(rs, 1);

        assert result != null;
        assertThat(result.getPublisher_id()).isEqualTo(1L);  // Check if publisher_id is mapped correctly
        assertThat(result.getPublisher()).isEqualTo("Rockstar");  // Check if publisher is mapped correctly
        assertThat(result.getGame_id()).isEqualTo(81);  // Check if game_id is mapped correctly
    }
}