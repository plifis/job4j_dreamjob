package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.model.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MemStore implements Store {
    private static final MemStore INST = new MemStore();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private final Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private final Map<String, User> users = new ConcurrentHashMap<>();
    private static AtomicInteger POST_ID = new AtomicInteger();
    private static AtomicInteger CANDIDATE_ID = new AtomicInteger();


    private MemStore() {
        posts.put(1, new Post(1, "Junior Java Job", "no experience"));
        posts.put(2, new Post(2, "Middle Java Job", "2-3 years experience"));
        posts.put(3, new Post(3, "Senior Java Job", "5+ years experience"));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }


    public void save(Post post) {
        if (post.getId() == 0) {
            post.setId(POST_ID.decrementAndGet());
        }
        posts.put(post.getId(), post);
    }

    public Post findPostById(int id) {
        return posts.get(id);
    }
    public Candidate findCandidateById(int id) {
        return candidates.get(id);
    }

    @Override
    public User findUserByEmail(String email) {
        return users.get(email);
    }


    public void save(Candidate candidate) {
        if (candidate.getId() == 0) {
            candidate.setId(CANDIDATE_ID.decrementAndGet());
        }
        candidates.put(candidate.getId(), candidate);
    }

    @Override
    public void save(User user) {
        users.put(user.getEmail(), user);
    }

    public static MemStore instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }
}