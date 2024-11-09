## Redis简介
Redis是一个基于内存的`key-value`结构数据库
    - 基于内存存储，读写性能高
    - 适合存储热点数据（热点商品、咨询、新闻）
https://www.redis.net.cn/

redis.conf 配置文件
src/redis-serve 服务端
src/redis-cli 客户端
    - keys *

redis-cli -h localhost -p 6379

## redis配置文件（redis.conf）
- requirepass 密码：配置密码，`redis-cli -h localhost -p 6379 -a 密码`

## redis数据类型
- 字符串 string
- 哈希 hash
- 列表 list
- 集合 set
- 有序集合 sorted set / zset

## redis常用命令
- 字符串操作命令
  - set key value: 设置指定key的值
  - get key: 获取指定key的值
  - setex key seconds value: 设置指定key的值，并将key的过期时间设置为seconds秒
  - setnx key value: 只有在key不存在时设置key的值

- 哈希操作命令
  - hset key field value: 将哈希表key中的字段field的值设为value
  - hget key field: 获取存储在哈希表中的指定字段
  - hdel key field: 删除存储在哈希表中的指定字段
  - hkeys key: 获取哈希表中所有的字段
  - hvals key: 获取哈希表中所有值

- 列表操作命令
  - lpush key value1 [value2]: 将一个或多个值插入到列表头部
  - lrange key start stop: 获取列表指定范围内的元素，`lrange key 0 -1`:查询列表所有元素
  - rpop key: 移除并获取列表最后一个元素
  - llen key: 获取列表长度

- 集合操作命令
  - sadd key member1 [member2]: 向集合添加一个或多个成员
  - smembers key: 返回集合中的所有成员
  - scard key: 获取集合的成员数
  - sinter key1 [key2]: 返回给定所有集合的交集
  - sunion key1 [key2]: 返回所有给定集合的并集
  - srem key member1 [member2]: 删除集合中一个或多个成员

- 有序集合操作命令
  - zadd key score1 member1 [score2 member2]: 向有序集合添加一个或多个成员
  - zrange key start stop [withscores]: 通过索引区间返回有序集合中指定区间内的成员
  - zincrby key increment member: 有序集合中对指定成员的分数加上增量 increment
  - zrem key member [member ...]: 移除有序集合中的一个或多个成员

- 通用命令
  - keys pattern: 查找所有符合给定模式（pattern）的key
  - exists key: 检查给定key是否存在
  - type key: 返回key所储存的值的类型
  - del key: 该命令在key存在时删除key

## java中如何操作redis
- jedis
- lettuce
- spring data redis
  - 导入spring data redis的maven坐标
  - 配置redis数据源
  - 编写配置类，创建redisTemplate对象
  - 通过redisTemplate对象操作redis


## HttpClient
> HttpClient是Apache Jakarta Common下的子项目，可以用于提供高效的、最新的、功能丰富的支持HTTP协议的客户端编程工具包，并且支持HTTP协议最新的版本和建议
```xml
<dependency>
  <groupId>org.apache.httpcomponents</groupId>
  <artifactId>httpclient</artifactId>
  <version>4.5.13</version>
</dependency>
```
核心API：
  - HttpClient
  - HttpClients
  - CloseableHttpClient
  - HttpGet
  - HttpPost

发送请求步骤：
  - 创建HttpClient对象
  - 创建Http请求对象
  - 调用HttpClient的execute方法发送请求

https://77717599.r5.cpolar.cn


进程问题：
```shell
lsof -i :8081
kill -9 <PID>
```


https://h5.ele.me/
