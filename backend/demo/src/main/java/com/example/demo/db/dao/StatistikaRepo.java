package com.example.demo.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.example.demo.db.DB;
import com.example.demo.models.Statistika;

public class StatistikaRepo {
    public Statistika getAll(){
        String sql = "SELECT * FROM statistika";
    
        try (Connection conn = DB.source().getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ) {
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int ukupnoVikendica = rs.getInt("ukupno_vikendica");
                int ukupnoVlasnika = rs.getInt("ukupno_vlasnika");
                int ukupnoTurista = rs.getInt("ukupno_turista");
                int rezervacije24h = rs.getInt("rezervacije_24h");
                int rezervacije7dana = rs.getInt("rezervacije_7dana");
                int rezervacije30dana = rs.getInt("rezervacije_30dana");

                return new Statistika(ukupnoVikendica, ukupnoVlasnika, ukupnoTurista, rezervacije24h, rezervacije7dana, rezervacije30dana);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;

    }
}
