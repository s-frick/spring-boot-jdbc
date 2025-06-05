package com.example.demo;

public interface IDGenerator<T extends ID, S> {
  T generate(S s);
}
