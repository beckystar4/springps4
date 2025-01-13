package com.example.springps4.mapper;

import com.example.springps4.model.response.GameResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameMapperTest {

    @InjectMocks
    GameMapper mapper;

    @Mock
    ResultSet rs;

    @Test
    void mapRow() throws SQLException {
        when(rs.getString("game_id")).thenReturn("81");
        when(rs.getString("title")).thenReturn("Knack");
        when(rs.getString("genres")).thenReturn("Platform");
        when(rs.getString("millions_of_copies_sold")).thenReturn("2");
        when(rs.getString("release_date")).thenReturn(String.valueOf(LocalDate.now()));

        GameResponse response = mapper.mapRow(rs, 1);

        assert response != null;
        assertThat(response.getGame_id()).isEqualTo(81L);
        assertThat(response.getTitle()).isEqualTo("Knack");
        assertThat(response.getGenres()).isEqualTo("Platform");
        assertThat(response.getMillions_of_copies_sold()).isEqualTo(2);
        assertThat(response.getRelease_date()).isEqualTo(LocalDate.now());
    }
}