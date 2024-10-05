import {Dev} from '../dev/dev';
import {Card} from '../card/card';

export class Comment {
  id: number;
  cards: Card;
  author: Dev;
  message: string;
  timeCreated: string;
}
