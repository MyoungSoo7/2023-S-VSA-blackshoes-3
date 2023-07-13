package com.travelvcommerce.uploadservice.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "video_urls")
public class VideoUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "video_id", referencedColumnName = "video_id", foreignKey = @ForeignKey(name = "video_url_fk"))
    private Video video;

    @Column(name = "video_s3_url", nullable = false)
    private String videoS3Url;

    @Column(name = "video_cloudfront_url", nullable = false)
    private String videoCloudfrontUrl;

    @Column(name = "thumbnail_s3_url", nullable = false)
    private String thumbnailS3Url;

    @Column(name = "thumbnail_cloudfront_url", nullable = false)
    private String thumbnailCloudfrontUrl;
}
