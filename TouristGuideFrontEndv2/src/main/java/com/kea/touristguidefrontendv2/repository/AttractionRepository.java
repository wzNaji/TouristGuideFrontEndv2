package com.kea.touristguidefrontendv2.repository;

import com.kea.touristguidefrontendv2.model.Attraction;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository

public class AttractionRepository {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;

    public AttractionRepository() {
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url,username,password);
    }

    // GET ALL
    public List<Attraction> getAttractions() {
        List<Attraction> attractions = new ArrayList<>();
        String query = "SELECT * FROM attractions";
        try(Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(query);
            ResultSet resultSet = stmt.executeQuery()) {

            while (resultSet.next()) {

                String name = resultSet.getString("name");
                String description = resultSet.getString("description");
                List<String> tags = getTagsByAttractionId(resultSet.getInt("attraction_id"));
                String city = getCityById(resultSet.getInt("city_id"));

                Attraction attraction = new Attraction(name,description,tags,city);
                attractions.add(attraction);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching attractions",e);
        }
        return attractions;
    }
    private List<String> getTagsByAttractionId(int attractionId) {
        List<String> tags = new ArrayList<>();
        String query = "SELECT Tags.tag_name " +
                "FROM Tags " +
                "INNER JOIN AttractionTags " +
                "ON Tags.tag_id = AttractionTags.tag_id " +
                "WHERE AttractionTags.attraction_id = ?";

        try (Connection con = getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, attractionId);

            try (ResultSet resultSet = stmt.executeQuery()) {
                while (resultSet.next()) {
                    tags.add(resultSet.getString("tag_name"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching tags", e);
        }
        return tags;
    }
    private String getCityById(int cityId) {
        String query = "SELECT city_name FROM Cities WHERE city_id = ?";
        try (Connection con = getConnection();
        PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setInt(1, cityId);
            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("city_name");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching city",e);
        }
        return null;
    }

    public Attraction getByName(String name) {
        String query = "SELECT * FROM Attractions WHERE name = ?";

        try(Connection con = getConnection();
            PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1,name);

            try (ResultSet resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String attractionName = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    List<String> tags = getTagsByAttractionId(resultSet.getInt("attraction_id"));
                    String city = getCityById(resultSet.getInt("city_id"));

                    return new Attraction(attractionName,description,tags, city);
                }
            }

        }
        catch (SQLException e) {
            throw new RuntimeException("Error fetching attraction by name",e);
        }
        return null;
    }

    public void addAttraction(String name, String description, List<String> tags, String city) {
        int cityId = getCityID(city);
        List<Integer> tagIds = getTagIds(tags);

        String insertAttractionSql = "INSERT INTO Attractions (name, description, city_id) VALUES (?,?,?)";

        try (Connection con = getConnection();
        PreparedStatement stmt = con.prepareStatement(insertAttractionSql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, name);
            stmt.setString(2, description);
            stmt.setInt(3, cityId);
            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating attraction failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    long newAttractionId = generatedKeys.getLong(1);
                    linkAttractionTags(newAttractionId, tagIds);
                } else {
                    throw new SQLException("Creating attraction failed, no ID obtained.");
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error adding new attraction", e);
        }
    }

    private void linkAttractionTags(long attractionId, List<Integer> tagIds) throws SQLException {
        String insertRelationSQL = "INSERT INTO AttractionTags (attraction_id, tag_id) VALUES (?, ?)";

        try (Connection con = getConnection();
             PreparedStatement pstmt = con.prepareStatement(insertRelationSQL)) {

            for (Integer tagId : tagIds) {
                pstmt.setLong(1, attractionId);
                pstmt.setInt(2, tagId);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error linking attraction to tags", e);
        }
    }

    private List<Integer> getTagIds(List<String> tags) {
        Map<String, Integer> tagMap = new HashMap<>();
        tagMap.put("Family friendly",1);
        tagMap.put("Free",2);
        tagMap.put("Outdoor",3);
        tagMap.put("Historic",4);

        List<Integer> tagIds = new ArrayList<>();
        for (String tag: tags) {
            Integer id = tagMap.get(tag);
            if (id == null) {
                throw new IllegalArgumentException("Unknown tag: " + tag);
            }
            tagIds.add(id);
        }
        return tagIds;
    } // Used in 'addAttraction'
    private int getCityID(String city) {
        return switch (city) {
            case "Copenhagen" -> 1;
            case "Odense" -> 2;
            case "Århus" -> 3;
            case "Haderslev" -> 4;
            default -> throw new IllegalArgumentException("Not a valid city: " + city);
        };
    } // Used in 'addAttraction'



    /*

    // UPDATE ATTRACTION
    public void updateAttraction(String name, String description, List<String> tags, String city) {
        Attraction attraction = getByName(name);
        attraction.setDescription(description);
        attraction.setTags(tags);
        attraction.setCity(city);
    }

    // DELETE
    public void deleteAttraction(String name) {
        Attraction attraction = getByName(name);
        attractions.remove(attraction);
    }

     */
}
