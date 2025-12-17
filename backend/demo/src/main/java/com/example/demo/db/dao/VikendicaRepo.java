package com.example.demo.db.dao;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.db.DB;
import com.example.demo.models.Vikendica;
import com.fasterxml.jackson.databind.ObjectMapper;

public class VikendicaRepo {
    public List<Vikendica> getAll(){
        String sql = "SELECT *  FROM vikendice";

        try(Connection conn = DB.source().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {
            
                ResultSet rs = statement.executeQuery();
                List<Vikendica> sve = new ArrayList<>();
                while(rs.next()){
                    String profilna = rs.getString("slika_url");
                    // Ako je null, učitaj default sliku i konvertuj u Base64
                    if (profilna == null || profilna.isEmpty()) {
                        Path defaultSlikaPath = Path.of("src/main/resources/static/images.jpg"); // primer putanje
                        byte[] slikaBytes = Files.readAllBytes(defaultSlikaPath);
                        profilna = Base64.getEncoder().encodeToString(slikaBytes);
                    }
                    sve.add(new Vikendica(rs.getInt("id"), rs.getString("naziv"), rs.getString("mesto"), rs.getString("opis"), rs.getFloat("cena_po_noci"), rs.getInt("vlasnik_id"), profilna, rs.getDate("datum_dodavanja"), rs.getInt("broj_soba")));
                }
                return sve;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;


    }

     public List<Vikendica> getByVlasnik(int vlasnikId) {
        String sql = "SELECT * FROM vikendice WHERE vlasnik_id = ?";
        List<Vikendica> lista = new ArrayList<>();

        try (Connection conn = DB.source().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, vlasnikId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                lista.add(new Vikendica(
                        rs.getInt("id"),
                        rs.getString("naziv"),
                        rs.getString("mesto"),
                        rs.getString("opis"),
                        rs.getFloat("cena_po_noci"),
                        rs.getInt("vlasnik_id"),
                        rs.getString("slika_url"),
                        rs.getDate("datum_dodavanja"),
                        rs.getInt("broj_soba")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean obrisiVikendicu(int id) {
        String sql = "DELETE FROM vikendice WHERE id = ?";
        try (Connection conn = DB.source().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Vikendica dodajVikendicu(Vikendica v, MultipartFile slika, MultipartFile jsonFajl) {
        try {
            if (jsonFajl != null && !jsonFajl.isEmpty()) {
                ObjectMapper mapper = new ObjectMapper();
                Vikendica izJsona = mapper.readValue(jsonFajl.getBytes(), Vikendica.class);
                if (izJsona.getNaziv() != null) v.setNaziv(izJsona.getNaziv());
                if (izJsona.getMesto() != null) v.setMesto(izJsona.getMesto());
                if (izJsona.getOpis() != null) v.setOpis(izJsona.getOpis());
                if (izJsona.getCena() != 0) v.setCena(izJsona.getCena());
                if (izJsona.getBroj_soba() != 0) v.setBroj_soba(izJsona.getBroj_soba());
            }

            String sql = "INSERT INTO vikendice (naziv, mesto, opis, broj_soba, cena_po_noci, vlasnik_id, slika_url) VALUES (?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = DB.source().getConnection();
                 PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, v.getNaziv());
                ps.setString(2, v.getMesto());
                ps.setString(3, v.getOpis());
                ps.setInt(4, v.getBroj_soba());
                ps.setFloat(5, v.getCena());
                ps.setInt(6, v.getVlasnik_id());

                if (slika == null || slika.isEmpty()) {
                    ps.setString(7, null);
                } else {
                    byte[] bytes = slika.getBytes();
                    String base64 = Base64.getEncoder().encodeToString(bytes);
                    ps.setString(7, base64);
                    v.setSlika(base64);
                }

                int rez = ps.executeUpdate();
                if (rez != 0) return v;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean izmeniVikendicu(Vikendica v) {
        String sql = "UPDATE vikendice SET naziv=?, mesto=?, opis=?, broj_soba=?, cena_po_noci=? WHERE id=?";
        try (Connection conn = DB.source().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, v.getNaziv());
            ps.setString(2, v.getMesto());
            ps.setString(3, v.getOpis());
            ps.setInt(4, v.getBroj_soba());
            ps.setFloat(5, v.getCena());
            ps.setInt(6, v.getId());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    
}
