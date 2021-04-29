package ru.job4j.dream.store;

import org.apache.commons.dbcp2.BasicDataSource;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

public class PsqlStore implements Store {

    private final BasicDataSource pool = new BasicDataSource();

    private PsqlStore() {
        Properties cfg = new Properties();
        try (BufferedReader io = new BufferedReader(
                new FileReader("db.properties"))) {
            cfg.load(io);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        try {
            Class.forName(cfg.getProperty("jdbc.driver"));
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
        pool.setDriverClassName(cfg.getProperty("jdbc.driver"));
        pool.setUrl(cfg.getProperty("jdbc.url"));
        pool.setUsername(cfg.getProperty("jdbc.username"));
        pool.setPassword(cfg.getProperty("jdbc.password"));
        pool.setMinIdle(5);
        pool.setMaxIdle(10);
        pool.setMaxOpenPreparedStatements(100);
    }

    private static final class Lazy {
        private static final Store INST = new PsqlStore();
    }

    public static Store instOf() {
        return Lazy.INST;
    }

    @Override
    public Collection<Post> findAllPosts() {
        List<Post> posts = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM post")) {
            try (ResultSet it = ps.executeQuery()) {
                while (it.next()) {
                    posts.add(new Post(it.getInt("id"), it.getString("name"),
                            it.getString("description")));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return posts;
    }

    @Override
    public Collection<Candidate> findAllCandidates() {
        List<Candidate> list = new ArrayList<>();
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement("SELECT * FROM candidate")) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Candidate(rs.getInt("id"), rs.getString("name")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void save(Post post) {
        if (post.getId() == 0) {
            createPost(post);
        } else {
            updatePost(post);
        }
    }

    private Post createPost(Post post) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO post(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, post.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    post.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return post;
    }

    private void updatePost(Post post) {
        if (post != null) {
            try (Connection cn = pool.getConnection();
                 PreparedStatement ps = cn.prepareStatement("UPDATE post SET name = (?), description = (?), created = (?)"
                         + "WHERE id = (?)")) {
                ps.setString(1, post.getName());
                ps.setString(2, post.getDescription());
                ps.setDate(3, (Date) post.getCreated());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("экземпляр Post не должен быть null");
        }
    }

    @Override
    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            createCandidate(candidate);
        } else {
            updateCandidate(candidate);
        }
    }


    private Candidate createCandidate(Candidate candidate) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO candidate(name) VALUES (?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, candidate.getName());
            ps.execute();
            try (ResultSet id = ps.getGeneratedKeys()) {
                if (id.next()) {
                    candidate.setId(id.getInt(1));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    private void updateCandidate(Candidate candidate) {
        if (candidate != null) {
            try (Connection cn = pool.getConnection();
                 PreparedStatement ps = cn.prepareStatement("UPDATE candidate SET name = (?)"
                         + "WHERE id = (?)")) {
                ps.setString(1, candidate.getName());
                ps.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            throw new IllegalArgumentException("экземпляр Candidate не должен быть null");
        }
    }


    @Override
    public void save(User user) {
            createUser(user);
    }


    private User createUser(User user) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "INSERT INTO users(name, email, password) "
                             + "VALUES (?, ?, ?)", PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.execute();
            try (ResultSet result = ps.getGeneratedKeys()) {
                if (result.next()) {
                    user.setId(result.getInt(1));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

//    private void updateUser(User user) {
//        if (user != null) {
//            try (Connection cn = pool.getConnection();
//                 PreparedStatement ps = cn.prepareStatement(
//                         "UPDATE user SET name = (?), email = (?), password = (?)")) {
//                ps.setString(1, user.getName());
//                ps.setString(2, user.getEmail());
//                ps.setString(3, user.getPassword());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        } else {
//            throw new IllegalArgumentException("Экземпляр User не должен быть null");
//        }
//    }

    @Override
    public Post findPostById(int id) {
        Post post = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM post WHERE id = (?)")) {
            ps.setString(1, String.valueOf(id));
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                post = new Post(result.getInt("id"), result.getString("name"),
                        result.getString("description"), result.getDate("created"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return post;
    }

    @Override
    public Candidate findCandidateById(int id) {
        Candidate candidate = null;
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM candidate WHERE id = (?)")) {
            ps.setString(1, String.valueOf(id));
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                candidate = new Candidate(result.getInt("id"), result.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return candidate;
    }

    @Override
    public User findUserByEmail(String email) {
        try (Connection cn = pool.getConnection();
             PreparedStatement ps = cn.prepareStatement(
                     "SELECT * FROM users WHERE email = (?)")) {
            ps.setString(1, email);
            ResultSet result = ps.executeQuery();
            if (result.next()) {
                User user = new User(
                        result.getString(2),
                        result.getString(3),
                        result.getString(4));
                user.setId(result.getInt(1));
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}