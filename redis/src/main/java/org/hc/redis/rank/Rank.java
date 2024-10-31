package org.hc.redis.rank;

import org.hc.redis.zset.ZSetRW;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * 场景：<br />
 * 如跳一跳这个小游戏进行说明，假设我们这个游戏用户遍布全球，因此我们要设计一个全球的榜单，每个玩家都会根据自己的战绩在排行榜中获取一个排名，我们需要支持全球榜单的查询，自己排位的查询这两种最基本的查询场景；此外当我的分数比上一次的高时，我需要更新我的积分，重新获得我的排名；
 *
 * 此外也会有一些高级的统计，比如哪个分段的人数最多，什么分段是瓶颈点，再根据地理位置计算平均分等等
 * @author 叽哒嘎叽
 * @since 2024/10/6
 */
@Component
public class Rank {
    private static final String GLOBAL_RANK = "global_rank";
    private final ZSetRW zSetRW;

    public Rank(ZSetRW zSetRW) {
        this.zSetRW = zSetRW;
    }

    public Long updateRank(Long userId, Float score) {
        zSetRW.add(GLOBAL_RANK, String.valueOf(userId), -score);

        Long rank = zSetRW.rank(GLOBAL_RANK, String.valueOf(userId));
        if (rank != null) {
            return rank  + 1;
        }
        return 999L;
    }
}
