package com.qoiu.dailytaskmotivator.data

import com.qoiu.dailytaskmotivator.Mapper
import com.qoiu.dailytaskmotivator.domain.Repository

abstract class AbstractRepository<Db : Mapper.Object<Db, Domain>, Domain>(
    private val dataSource: RealmDataSource<Db>,
    private val domainMapper: Mapper.Data<Db, Domain>,
    private val dbMapper: Mapper.Data<Domain, Db>
) : Repository<Domain> {
    override suspend fun fetchData(): List<Domain> {
            val list = mutableListOf<Domain>()
            dataSource.read().forEach {
                list.add(domainMapper.map(it))
            }
            return list
        }

    override suspend fun save(data: Domain) {
        dataSource.save(dbMapper.map(data))
    }

    override suspend fun remove(data: Domain) {
        dataSource.remove(dbMapper.map(data))
    }

}