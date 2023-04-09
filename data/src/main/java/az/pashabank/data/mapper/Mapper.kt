package az.pashabank.data.mapper

import kotlinx.serialization.KSerializer

interface Mapper<Domain, Data> {
    fun toData(domain: Domain): Data
    fun toDomain(data: Data): Domain
}

interface SerializableMapper<Domain, Data> : Mapper<Domain, Data> {
    fun getSerializer(): KSerializer<Data>
}