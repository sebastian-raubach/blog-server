/* tslint:disable */
/* eslint-disable */
// Generated using typescript-generator version 3.2.1263 on 2026-08-23 09:57:39.

export interface Hikeratings extends Serializable {
    postId: number;
    weather: number;
    path: number;
    view: number;
}

export interface Hikestats extends Serializable {
    postId: number;
    duration: number;
    distance: number;
    ascent: number;
    gpxPath: string;
    elevationProfilePath: string;
    timeDistanceProfilePath: string;
    createdOn: Date;
    updatedOn: Date;
}

export interface Hills extends Serializable {
    id: number;
    name: string;
    type: HillsType;
    region: string;
    url: string;
    latitude: number;
    longitude: number;
    elevation: number;
    createdOn: Date;
    updatedOn: Date;
}

export interface ImageDetails extends Serializable {
    imageId: number;
    imagePath: string;
    postId: number;
    isPrimary: boolean;
    description: string;
}

export interface Images extends Serializable {
    id: number;
    path: string;
    latitude: number;
    longitude: number;
    elevation: number;
    createdOn: Date;
    updatedOn: Date;
}

export interface Individuals extends Serializable {
    id: number;
    name: string;
    userId: number;
    photo: any;
    createdOn: Date;
    updatedOn: Date;
}

export interface PostIndividuals extends Serializable {
    id: number;
    postId: number;
    individualId: number;
    createdOn: Date;
    updatedOn: Date;
}

export interface Posthills extends Serializable {
    postId: number;
    hillId: number;
    successful: boolean;
}

export interface Postimages extends Serializable {
    postId: number;
    imageId: number;
    isPrimary: boolean;
    description: string;
}

export interface Posts extends Serializable {
    id: number;
    type: PostsType;
    title: string;
    content: string;
    contentMarkdown: string;
    visible: boolean;
    viewCount: number;
    endDate: Date;
    createdOn: Date;
    updatedOn: Date;
}

export interface Postsites extends Serializable {
    postId: number;
    siteId: number;
    groundtype: PostsitesGroundtype;
}

export interface Postusers extends Serializable {
    postId: number;
    userId: number;
}

export interface Postvideos extends Serializable {
    postId: number;
    videoPath: string;
    createdOn: Date;
    updatedOn: Date;
}

export interface Relationships extends Serializable {
    postAId: number;
    postBId: number;
    createdOn: Date;
    updatedOn: Date;
}

export interface SchemaVersion extends Serializable {
    installedRank: number;
    version: string;
    description: string;
    type: string;
    script: string;
    checksum: number;
    installedBy: string;
    installedOn: Date;
    executionTime: number;
    success: boolean;
}

export interface Sites extends Serializable {
    id: number;
    name: string;
    description: string;
    sitetype: SitesSitetype;
    latitude: number;
    longitude: number;
    elevation: number;
    rating: SiteRating;
    facilities: SiteFacilities;
    createdOn: Date;
    updatedOn: Date;
}

export interface Stories extends Serializable {
    id: number;
    title: string;
    content: string;
    contentMarkdown: string;
    createdOn: Date;
    updatedOn: Date;
}

export interface Storyposts extends Serializable {
    storyId: number;
    postId: number;
}

export interface Users extends Serializable {
    id: number;
    username: string;
    password: string;
    displayname: string;
    photo: any;
    createdOn: Date;
    updatedOn: Date;
}

export interface ViewHills extends Serializable {
    hillId: number;
    hillName: string;
    hillType: HillsType;
    hillRegion: string;
    hillUrl: string;
    hillLatitude: number;
    hillLongitude: number;
    hillElevation: number;
    posts: MiniPost[];
    people: PostPerson[];
}

export interface ViewPosts extends Serializable {
    postId: number;
    postType: PostsType;
    postTitle: string;
    postContent: string;
    postMarkdown: string;
    postVisible: boolean;
    postViewCount: number;
    postStartDate: Date;
    postEndDate: Date;
    hills: PostHill[];
    people: PostPerson[];
    hikestats: HikeStats;
    hikerating: HikeRating;
    videos: string[];
    storyIds: number[];
    images: PostImage[];
    sites: PostSite[];
}

export interface ViewSites extends Serializable {
    id: number;
    name: string;
    description: string;
    sitetype: SitesSitetype;
    latitude: number;
    longitude: number;
    elevation: number;
    rating: SiteRating;
    facilities: SiteFacilities;
    createdOn: Date;
    updatedOn: Date;
    groundtypes: PostsitesGroundtype[];
    postIds: number[];
}

export interface ViewStories extends Serializable {
    storyId: number;
    storyTitle: string;
    storyContent: string;
    storyMarkdown: string;
    storyStartDate: Date;
    posts: MiniPost[];
}

export interface Hill extends Hills {
    hillIndividuals: Individuals[];
}

export interface HillRequest extends PaginatedRequest {
    postId: number;
    hillName: string;
}

export interface HillTypeCount {
    type: HillsType;
    count: number;
}

export interface HillWithPosts extends Hill {
    posts: Posts[];
}

export interface IndividualRecord {
    individual: Individuals;
    postIndividuals: PostIndividuals[];
}

export interface LoginDetails {
    username: string;
    password: string;
}

export interface MiniPost {
    id: number;
    title: string;
    primaryImageId: number;
    primaryImagePath: string;
    visible: number;
    type: PostsType;
}

export interface PaginatedRequest {
    searchTerm: string;
    orderBy: string;
    ascending: number;
    limit: number;
    page: number;
    prevCount: number;
}

export interface Post extends Posts {
    images: ImageDetails[];
    videos: Postvideos[];
    postIndividuals: Individuals[];
}

export interface PostEditPayload {
    markdown: string;
    imageDescriptions: { [index: string]: string };
}

export interface PostImport {
    type: PostsType;
    title: string;
    content: string;
    contentMarkdown: string;
    visible: boolean;
    individuals: number[];
    endDate: Date;
    createdOn: Date;
    updatedOn: Date;
    hills: PostHill[];
    videos: string[];
    stats: Hikestats;
    rating: Hikeratings;
}

export interface PostRequest extends PaginatedRequest {
    year: number;
    postType: PostsType;
    siteId: number;
    hillId: number;
    storyId: number;
    relatedPostId: number;
}

export interface Settings {
    googleAnalyticsKey: string;
}

export interface SiteFacilities {
    toilets: boolean;
    showers: boolean;
    shop: boolean;
    restaurant: boolean;
    cafe: boolean;
    electricHookup: boolean;
    localDogWalk: boolean;
    wifi: boolean;
}

export interface SiteRating {
    scenery: number;
    location: number;
    price: number;
    facilities: number;
}

export interface Story extends Stories {
    posts: MiniPost[];
}

export interface Token {
    token: string;
    imageToken: string;
    id: number;
    username: string;
    lifetime: number;
    createdOn: number;
}

export interface YearCount {
    year: number;
    count: number;
}

export interface Serializable {
}

export interface PostPerson {
    personId: number;
    personName: string;
}

export interface PostHill {
    hillId: number;
    hillName: string;
    hillType: HillsType;
    hillLatitude: number;
    hillLongitude: number;
    hillElevation: number;
    hillSuccessful: number;
}

export interface HikeStats {
    duration: number;
    distance: number;
    ascent: number;
    gpx: string;
    elevationProfile: string;
    timeDistanceProfile: string;
    individualStats: { [index: string]: Section[] };
}

export interface HikeRating {
    weather: number;
    path: number;
    view: number;
}

export interface PostImage {
    imageId: number;
    imagePath: string;
    imageIsPrimary: number;
    imageDescription: string;
}

export interface PostSite {
    siteId: number;
    siteName: string;
    siteDescription: string;
    siteType: SitesSitetype;
    siteLatitude: number;
    siteLongitude: number;
    siteRating: SiteRating;
    siteFacilities: SiteFacilities;
    groundType: PostsitesGroundtype;
}

export interface Section {
    from: number;
    to: number;
    type: MovementType;
}

export const enum HillsType {
    munro = 'munro',
    corbett = 'corbett',
    graham = 'graham',
    donald = 'donald',
    sub2000 = 'sub2000',
    wainwright = 'wainwright',
    hewitt = 'hewitt',
    other = 'other',
}

export const enum PostsType {
    hike = 'hike',
    news = 'news',
}

export const enum PostsitesGroundtype {
    paved = 'paved',
    grass = 'grass',
    gravel = 'gravel',
    sand = 'sand',
}

export const enum SitesSitetype {
    campsite = 'campsite',
    wildcamp = 'wildcamp',
}

export const enum MovementType {
    BIKE = 'BIKE',
    WALK = 'WALK',
    RUN = 'RUN',
    TRAILER = 'TRAILER',
    SWIM = 'SWIM',
}
