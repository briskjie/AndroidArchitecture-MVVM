/*
 * Copyright (C) 2016 Francisco José Montiel Navarro.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.xiangxuchen.architecture_mvvm_opt.network.cookie;


import android.content.Context;


import com.example.xiangxuchen.architecture_mvvm_opt.network.cookie.cache.CookieCache;
import com.example.xiangxuchen.architecture_mvvm_opt.network.cookie.cache.SetCookieCache;
import com.example.xiangxuchen.architecture_mvvm_opt.network.cookie.persistence.CookiePersistor;
import com.example.xiangxuchen.architecture_mvvm_opt.network.cookie.persistence.SharedPrefsCookiePersistor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.HttpUrl;

public class OkCookieManager implements ClearableCookieJar {

    private static OkCookieManager sInstance;


    private CookieCache cache;
    private CookiePersistor persistor;

    private OkCookieManager(CookieCache cache, CookiePersistor persistor) {
        this.cache = cache;
        this.persistor = persistor;
        this.cache.addAll(persistor.loadAll());
    }

    public static OkCookieManager getInstance(Context context) {
        if (sInstance == null) {
            synchronized (OkCookieManager.class) {
                if (sInstance == null) {
                    sInstance = new OkCookieManager(new SetCookieCache(), new SharedPrefsCookiePersistor(context.getApplicationContext()));
                }
            }
        }
        return sInstance;
    }


    @Override
    synchronized public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        cache.addAll(cookies);
        persistor.saveAll(filterPersistentCookies(cookies));
    }

    private static List<Cookie> filterPersistentCookies(List<Cookie> cookies) {
        List<Cookie> persistentCookies = new ArrayList<>();

        for (Cookie cookie : cookies) {
            if (cookie.persistent()) {
                persistentCookies.add(cookie);
            }
        }
        return persistentCookies;
    }

    @Override
    synchronized public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookiesToRemove = new ArrayList<>();
        List<Cookie> validCookies = new ArrayList<>();

        for (Iterator<Cookie> it = cache.iterator(); it.hasNext(); ) {
            Cookie currentCookie = it.next();

            if (isCookieExpired(currentCookie)) {
                cookiesToRemove.add(currentCookie);
                it.remove();
            } else if (currentCookie.matches(url)) {
                validCookies.add(currentCookie);
            }
        }

        persistor.removeAll(cookiesToRemove);

        return validCookies;
    }

    private static boolean isCookieExpired(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }


    public List<Cookie> getCookies() {
        return cache.getCookies();
    }


    /**
     * clear cookies that only work in current session
     */
    @Override
    public synchronized void clearSession() {
        cache.clear();
        cache.addAll(persistor.loadAll());
    }

    @Override
    public synchronized void clear() {
        cache.clear();
        persistor.clear();
    }

    public synchronized void clearCookie(String url) {
         String tUrl = url.endsWith("/") ? url : url + "/";
        List<Cookie> removeCookies = new ArrayList<>();
        for (Cookie cookie : cache.getCookies()) {
            if (cookie.matches(HttpUrl.parse(tUrl))) {
                removeCookies.add(cookie);
            }
        }
        cache.clear();
        persistor.removeAll(removeCookies);
        cache.addAll(persistor.loadAll());
    }
}
