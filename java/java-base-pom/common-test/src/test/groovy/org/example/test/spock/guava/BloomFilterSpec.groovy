package org.example.test.spock.guava

import com.google.common.hash.BloomFilter
import com.google.common.hash.Funnels
import com.google.common.io.BaseEncoding
import spock.lang.Specification

import java.nio.charset.Charset

class BloomFilterSpec extends Specification {

    /**
     * 布隆过滤器测试
     */
    def "testBloomFilter"() {
        given:
        def exist = [] as List<String>
        def size = 10000
        def bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), size, 0.0001)
        for (int i = 0; i < size; i++) {
            def key = UUID.randomUUID().toString()
            bloomFilter.put(key)
            exist.add(key)
        }

        exist.forEach({
            if (!bloomFilter.mightContain(it)) {
                System.err.println("有逃犯越狱了");
            }
        })
        def start = System.currentTimeMillis()
        def out = new ByteArrayOutputStream()
        bloomFilter.writeTo(out)
        def array = out.toByteArray()
        def end = System.currentTimeMillis()
        println ((end - start) / 1000)
        def str = BaseEncoding.base64().encode(array)
        out.close()

        start = System.currentTimeMillis();
        def arr = BaseEncoding.base64().decode(str)
        def input = new ByteArrayInputStream(arr)
        def newFilter = BloomFilter.readFrom(input, Funnels.stringFunnel(Charset.defaultCharset()))
        input.close()
        end = System.currentTimeMillis()
        println ((end - start) / 1000)

        exist.forEach({
            if (!newFilter.mightContain(it)) {
                System.err.println("有逃犯越狱了");
            }
        })

        expect:
        println str.length() / 1024.0
    }
}
