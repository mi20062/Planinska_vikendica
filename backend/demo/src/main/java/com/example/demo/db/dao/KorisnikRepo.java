package com.example.demo.db.dao;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

/*import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;*/

import com.example.demo.db.DB;
import com.example.demo.models.Korisnik;
public class KorisnikRepo {
    //private File profileImgsDir = new File("backend/demo/src/main/resources/static");
    //private final PasswordEncoder encoder = new BCryptPasswordEncoder();
    public Korisnik login(Korisnik k){
        String sql = "select * from korisnici where korisnicko_ime=? and lozinka =?";
        try (Connection conn = DB.source().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)){
            statement.setString(1, k.getKorisnicko_ime());
            statement.setString(2, k.getLozinka());

            ResultSet rs = statement.executeQuery();
            
        if(rs.next()){
            String profilna = rs.getString("profilna_slika");
            // Ako je null, učitaj default sliku i konvertuj u Base64
            if (profilna == null || profilna.isEmpty()) {
                ClassPathResource defaultImg = new ClassPathResource("static/images.jpg");
                byte[] slikaBytes = defaultImg.getInputStream().readAllBytes();
                profilna = Base64.getEncoder().encodeToString(slikaBytes);
            }
            Korisnik korisnik = new Korisnik(
            rs.getInt("id"),
            rs.getString("korisnicko_ime"),
            rs.getString("ime"),
            rs.getString("prezime"),
            rs.getString("lozinka"),
            rs.getString("uloga"),
            rs.getString("email"),
            rs.getString("pol"),
            rs.getString("adresa"),
            rs.getString("kontakt_telefon"),
            profilna,
            rs.getString("broj_kreditne_kartice"));

            return korisnik;
        }

            
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return null;
    }

    public Korisnik azuriraj(Korisnik u, MultipartFile profile_img){
        
        String sql = "update korisnici set ime=?, prezime=?, adresa=?, email=?, broj_kreditne_kartice=?, profilna_slika=?, kontakt_telefon=? where id=?";
        try (Connection conn = DB.source().getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

                statement.setString(1, u.getIme());
                statement.setString(2, u.getPrezime());
                statement.setString(3, u.getAdresa());
                statement.setString(4, u.getEmail());
                statement.setString(5, u.getBroj_kreditne_kartice());
                System.err.println(u.getBroj_kreditne_kartice().length() + ": " + u.getBroj_kreditne_kartice());
                
                // profilna slika: samo ako postoji nova, inače zadrži staru
                if(profile_img == null || profile_img.isEmpty()){
                    // zadrži staru vrednost
                    statement.setString(6, u.getProfilna_slika());
                } else {
                    byte[] imageBytes = profile_img.getBytes();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    statement.setString(6, base64Image);
                    u.setProfilna_slika(base64Image);
                }
                statement.setString(7, u.getKontakt_telefon());
                statement.setInt(8, u.getId());

                if(statement.executeUpdate() != 0)
                    return u;
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Korisnik pronadjiID(int id) {
        Korisnik korisnik = null;

        String sql = "SELECT * FROM korisnici WHERE id = ?";

        try (Connection conn = DB.source().getConnection(); // tvoja metoda za konekciju
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    korisnik = new Korisnik(
                        rs.getInt("id"),
                        rs.getString("korisnicko_ime"),
                        rs.getString("ime"),
                        rs.getString("prezime"),
                        rs.getString("uloga"),
                        rs.getString("email"),
                        rs.getString("lozinka"),
                        rs.getString("pol"),
                        rs.getString("broj_kreditne_kartice"),
                        rs.getString("kontakt_telefon"),
                        rs.getString("adresa"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return korisnik;
    }

    public Korisnik registruj(Korisnik korisnik,
                          MultipartFile profilna_slika) {
                            
    String sql = "INSERT INTO korisnici " +
            "(korisnicko_ime, ime, prezime, uloga, email, lozinka, pol, broj_kreditne_kartice, kontakt_telefon, adresa, profilna_slika) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = DB.source().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        
        ps.setString(1, korisnik.getKorisnicko_ime());
        ps.setString(2, korisnik.getIme());
        ps.setString(3, korisnik.getPrezime());
        ps.setString(4, korisnik.getUloga());
        ps.setString(5, korisnik.getEmail());
        ps.setString(6, korisnik.getLozinka());
        ps.setString(7, korisnik.getPol());
        ps.setString(8, korisnik.getBroj_kreditne_kartice());
        ps.setString(9, korisnik.getKontakt_telefon());
        ps.setString(10, korisnik.getAdresa());

        // Profilna slika: Base64 ako postoji
        //ps.setString(11, null);
        // profilna slika: samo ako postoji nova, inače zadrži staru
                if(profilna_slika == null || profilna_slika.isEmpty()){
                    // zadrži staru vrednost
                    ps.setString(11, null);
                } else {
                    byte[] imageBytes = profilna_slika.getBytes();
                    String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                    ps.setString(11, base64Image);
                    korisnik.setProfilna_slika(base64Image);
                }

        int rez = ps.executeUpdate();
        System.out.println("Cao1");
        if (rez != 0) {
            System.out.println("Cao");
            // Kreiramo korisnika sa postojećim konstruktorom (bez profilne slike)
            
            return korisnik;
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return null;
}

public boolean deaktiviraj(int id) {
    String sql = "UPDATE korisnici SET aktivan = 0 WHERE id = ?";
    try (Connection conn = DB.source().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

public List<Korisnik> getPending() {
    String sql = "SELECT * FROM korisnici WHERE status = 'pending'";
    List<Korisnik> lista = new ArrayList<>();
    try (Connection conn = DB.source().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ResultSet rs = ps.executeQuery();
        while(rs.next()) {
            lista.add(new Korisnik(
                rs.getInt("id"),
                rs.getString("korisnicko_ime"),
                rs.getString("ime"),
                rs.getString("prezime"),
                rs.getString("lozinka"),
                rs.getString("uloga"),
                rs.getString("email"),
                rs.getString("pol"),
                rs.getString("adresa"),
                rs.getString("kontakt_telefon"),
                rs.getString("broj_kreditne_kartice"),
                rs.getString("profilna_slika")
            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}

public boolean prihvatiZahtev(int id) {
    String sql = "UPDATE korisnici SET status='accepted', aktivan=1 WHERE id=?";
    try (Connection conn = DB.source().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}

public boolean odbijZahtev(int id) {
    String sql = "UPDATE korisnici SET status='rejected', aktivan=0 WHERE id=?";
    try (Connection conn = DB.source().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
        ps.setInt(1, id);
        return ps.executeUpdate() > 0;
    } catch (Exception e) {
        e.printStackTrace();
    }
    return false;
}
public List<Korisnik> getAll() {
    String sql = "SELECT * FROM korisnici";
    List<Korisnik> lista = new ArrayList<>();
    try (Connection conn = DB.source().getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
        
        while (rs.next()) {
            String profilna = rs.getString("profilna_slika");
            /*if (profilna == null || profilna.isEmpty()) {
                Path defaultSlikaPath = Path.of("src/main/resources/static/images.jpg");
                byte[] slikaBytes = Files.readAllBytes(defaultSlikaPath);
                profilna = Base64.getEncoder().encodeToString(slikaBytes);
            }*/

            lista.add(new Korisnik(
                rs.getInt("id"),
                rs.getString("korisnicko_ime"),
                rs.getString("ime"),
                rs.getString("prezime"),
                rs.getString("lozinka"),
                rs.getString("uloga"),
                rs.getString("email"),
                rs.getString("pol"),
                rs.getString("adresa"),
                rs.getString("kontakt_telefon"),
                profilna,
                rs.getString("broj_kreditne_kartice"),
                rs.getInt("aktivan"),
                rs.getString("status")

            ));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return lista;
}

public boolean obrisi(int id) {
    String sql = "DELETE FROM korisnici WHERE id=?";
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
