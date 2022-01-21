package com.qoiu.dailytaskmotivator.data

import com.qoiu.dailytaskmotivator.*

interface RealmDataSource<T> : Read<List<T>>, Save<T>, Remove<T>