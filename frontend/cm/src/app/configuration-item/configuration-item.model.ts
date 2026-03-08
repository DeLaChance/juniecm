export interface ConfigurationItem {
  id: string;
  name: string;
  type: 'hardware' | 'software';
  startDate: string;
  endDate: string | null;
  children?: ConfigurationItem[];
}

export interface ConfigurationItemResponse {
  data: ConfigurationItem[];
  totalCount: number;
}
