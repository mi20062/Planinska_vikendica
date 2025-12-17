package com.example.demo.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.example.demo.db.DB;
import com.example.demo.models.Rezervacija;
import com.example.demo.models.Vikendica;



public class RezervacijaRepo {

    public Rezervacija posaljiRezervaciju(Rezervacija r){

        String sql = "insert into rezervacije (vikendica_id, turista_id, datum_od, datum_do, status) values(?,?,?,?,?)";
        try (Connection conn = DB.source().getConnection();
            PreparedStatement statement = conn.prepareStatement(sql)) {
            
            statement.setInt(1, r.getVikendica_id());
            statement.setInt(2, r.getTurista_id());
            statement.setDate(3,r.getDatum_od());
            statement.setDate(4, r.getDatum_do());
            
            statement.setString(5, r.getStatus());

            int res = statement.executeUpdate();
            if(res != 0)
                return r;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<Rezervacija> getAll(){
        String sql = "SELECT *  FROM rezervacije";

        try(Connection conn = DB.source().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
                ResultSet rs = statement.executeQuery();
                List<Rezervacija> sve = new ArrayList<>();
                while(rs.next()){
                    sve.add(new Rezervacija(rs.getInt("id"), rs.getInt("vikendica_id"), rs.getInt("turista_id"), rs.getDate("datum_od"), rs.getDate("datum_do"), rs.getString("status"),rs.getString("komentar")));
                }
                return sve;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;


    }

    public boolean potvrdiRezervaciju(int id) {
    String sql = "UPDATE rezervacije SET status = 'rezervisano' WHERE id = ?";
    try (Connection conn = DB.source().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

public boolean odbijRezervaciju(int id, String komentar) {
    String sql = "UPDATE rezervacije SET status = 'zavrseno', komentar = ? WHERE id = ?";
    try (Connection conn = DB.source().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setString(1, komentar);
        ps.setInt(2, id);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


public Map<String, Map<String, Integer>> rezervacijePoMesecima(int vlasnikId) {
    Map<String, Map<String, Integer>> rezultat = new HashMap<>();
    String sql = """
        SELECT v.naziv AS vikendica, MONTH(r.datum_od) AS mesec, COUNT(*) AS broj
        FROM rezervacije r
        JOIN vikendice v ON r.vikendica_id = v.id
        WHERE v.vlasnik_id = ? AND r.status = 'rezervisano'
        GROUP BY v.naziv, MONTH(r.datum_od)
    """;

    try (Connection conn = DB.source().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, vlasnikId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String vik = rs.getString("vikendica");
            String mesec = String.valueOf(rs.getInt("mesec"));
            int broj = rs.getInt("broj");

            rezultat.computeIfAbsent(vik, k -> new HashMap<>()).put(mesec, broj);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return rezultat;
}

public Map<String, Map<String, Integer>> vikendVsRadniDani(int vlasnikId) {
    Map<String, Map<String, Integer>> rezultat = new HashMap<>();
    String sql = """
        SELECT v.naziv AS vikendica,
               SUM(CASE WHEN DAYOFWEEK(r.datum_od) IN (1,7) THEN 1 ELSE 0 END) AS vikend,
               SUM(CASE WHEN DAYOFWEEK(r.datum_od) BETWEEN 2 AND 6 THEN 1 ELSE 0 END) AS radni
        FROM rezervacije r
        JOIN vikendice v ON r.vikendica_id = v.id
        WHERE v.vlasnik_id = ? AND r.status = 'rezervisano'
        GROUP BY v.naziv
    """;

    try (Connection conn = DB.source().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, vlasnikId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String vik = rs.getString("vikendica");
            int vikend = rs.getInt("vikend");
            int radni = rs.getInt("radni");

            Map<String, Integer> podaci = new HashMap<>();
            podaci.put("vikend", vikend);
            podaci.put("radni", radni);
            rezultat.put(vik, podaci);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return rezultat;
}

public boolean otkaziRezervaciju(int id) {
    String sql = "UPDATE rezervacije SET status = 'otkazano' WHERE id = ?";
    try (Connection conn = DB.source().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}


}
