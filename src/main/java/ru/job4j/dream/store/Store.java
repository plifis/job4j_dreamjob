package ru.job4j.dream.store;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Store {
    private static final Store INST = new Store();
    private final Map<Integer, Post> posts = new ConcurrentHashMap<>();
    private Map<Integer, Candidate> candidates = new ConcurrentHashMap<>();
    private static AtomicInteger POST_ID = new AtomicInteger();


    private Store() {
        posts.put(1, new Post(1, "Junior Java Job", "no experience", new Date()));
        posts.put(2, new Post(2, "Middle Java Job", "2-3 years experience", new Date()));
        posts.put(3, new Post(3, "Senior Java Job", "5+ years experience", new Date()));
        candidates.put(1, new Candidate(1, "Junior Java"));
        candidates.put(2, new Candidate(2, "Middle Java"));
        candidates.put(3, new Candidate(3, "Senior Java"));
    }

    public void save(Post post) {
        post.setId(POST_ID.decrementAndGet());
        posts.put(post.getId(), post);
    }

    public static Store instOf() {
        return INST;
    }

    public Collection<Post> findAllPosts() {
        return posts.values();
    }

    public Collection<Candidate> findAllCandidates() {
        return candidates.values();
    }
}