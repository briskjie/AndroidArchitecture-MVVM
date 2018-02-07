package com.example.xiangxuchen.architecture_mvvm_opt.di.components;

import com.example.xiangxuchen.architecture_mvvm_opt.di.PerActivity;
import com.example.xiangxuchen.architecture_mvvm_opt.di.modules.ActivityModule;
import com.example.xiangxuchen.architecture_mvvm_opt.ui.MainActivity;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import dagger.Component;

/**
 * Created by xiangxuchen on 2018/2/5.
 * <p>
 * A base component on which fragment's component may depend.
 * Activity-level components should extend this component
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity activity);

    //Expose to sub-graphs
    RxAppCompatActivity rxAppCompatActivity();
}
