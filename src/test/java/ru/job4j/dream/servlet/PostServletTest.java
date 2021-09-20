package ru.job4j.dream.servlet;


import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.MemStore;
import ru.job4j.dream.store.PsqlStore;
import ru.job4j.dream.store.Store;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest(PsqlStore.class)
public class PostServletTest {

    @Test
    public void whenAddPost() throws ServletException, IOException {
        Store store = MemStore.instOf();
        HttpServletRequest req = mock(HttpServletRequest.class);
        HttpServletResponse resp = mock(HttpServletResponse.class);
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        when(req.getParameter("id")).thenReturn("1");
        when(req.getParameter("name")).thenReturn("java");
        when(req.getParameter("description")).thenReturn("desc");
        new PostServlet().doPost(req, resp);
        assertThat(store.findPostById(1).getName(), is("java"));

    }

    @Test
    public void whenGetAllPosts() {
        Store store = MemStore.instOf();
        PowerMockito.mockStatic(PsqlStore.class);
        Mockito.when(PsqlStore.instOf()).thenReturn(store);
        assertThat(store.findAllCandidates().toString(), is (List.of(
                new Candidate(1, "Junior Java", 1),
                new Candidate(2, "Middle Java", 1),
                new Candidate(3, "Senior Java", 2)).toString()));

    }

}