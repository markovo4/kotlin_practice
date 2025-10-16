package todolist.utils

import org.ktorm.schema.Column
import org.ktorm.schema.SqlType
import org.ktorm.schema.Table
import org.ktorm.schema.VarcharSqlType

inline fun <reified T : Enum<T> > Table<*>.enumColumn (
    name: String,
    sqlType: SqlType<String> = VarcharSqlType
): Column<T> {
    return registerColumn(name, sqlType).transform(
        { value -> enumValueOf<T>(value) },
        { it.name }
    )
}